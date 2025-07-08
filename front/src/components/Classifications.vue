<template>
  <div class="classifications-outer">
    <div class="classifications-container">
      <div class="controls-row">
        <h2>Classifications</h2>
        <input v-model="searchInput" placeholder="Search documents..." class="styled-select" />
        <span style="display: inline-block; width: auto">Higher than</span>
        <input
          type="text"
          v-model="filterRangeInput"
          placeholder="e.g. 0-100 or 15-85 or 70"
          class="styled-select"
        />
      </div>
      <table>
        <thead>
          <tr>
            <th @click="sort('document')" style="cursor: pointer">
              Document
              <span :style="sortBy === 'document' ? 'color: purple' : ''">
                <template v-if="sortBy === 'document'">
                  <span v-if="sortDir === 'asc'">▲</span>
                  <span v-else>▼</span>
                </template>
              </span>
            </th>
            <th>Main Classification</th>
            <th @click="sort('probability')" style="cursor: pointer; text-align: right">
              Probability
              <span :style="sortBy === 'probability' ? 'color: purple' : ''">
                <template v-if="sortBy === 'probability'">
                  <span v-if="sortDir === 'asc'">▲</span>
                  <span v-else>▼</span>
                </template>
              </span>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="doc in sortedAndFilteredDocuments" :key="doc.id">
            <td>{{ doc.document_name }}</td>
            <td @click="editingDocId = doc.id" style="cursor: pointer">
              <template v-if="editingDocId === doc.id">
                <select
                  v-model="mainClassificationLabel[doc.id]"
                  @change="updateMainClassification(doc, mainClassificationLabel[doc.id])"
                  @blur="editingDocId = null"
                  autofocus
                  class="styled-select"
                >
                  <option v-for="c in doc.classifications" :key="c.label" :value="c.label">
                    {{ c.label }}
                  </option>
                </select>
              </template>
              <template v-else>
                {{ getMainClassification(doc).label }}
                <span v-if="doc.edited" class="edited-badge">edited</span>
              </template>
            </td>
            <td>{{ Math.round(getMainClassification(doc).score * 100) }}%</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'

interface Classification {
  label: string
  score: number
  editing?: boolean
  edited?: boolean
}
interface DocumentClassifications {
  id: number
  document_name: string
  classifications: Classification[]
  edited?: boolean
}

const documents = ref<DocumentClassifications[]>([])
const searchInput = ref('')
const filterRangeInput = ref('')
const search = ref('')
const filterRange = ref('')
const editingDocId = ref<number | null>(null)
const mainClassificationLabel = ref<{ [id: number]: string }>({})
const sortBy = ref<'document' | 'probability'>('document')
const sortDir = ref<'asc' | 'desc'>('asc')

let debounceSearch: ReturnType<typeof setTimeout> | null = null
let debounceFilter: ReturnType<typeof setTimeout> | null = null

watch(searchInput, (val) => {
  if (debounceSearch) clearTimeout(debounceSearch)
  debounceSearch = setTimeout(() => {
    search.value = val
  }, 300)
})
watch(filterRangeInput, (val) => {
  if (debounceFilter) clearTimeout(debounceFilter)
  debounceFilter = setTimeout(() => {
    filterRange.value = val
  }, 300)
})

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/classifications')
    const data: DocumentClassifications[] = await response.json()
    documents.value = data
    if (data.length > 0) {
      data.forEach((doc) => {
        mainClassificationLabel.value[doc.id] = getMainClassification(doc).label
      })
    }
  } catch (e) {
    console.error('Failed to fetch classifications', e)
  }
})

function sort(field: 'document' | 'probability') {
  if (sortBy.value === field) {
    sortDir.value = sortDir.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortBy.value = field
    sortDir.value = 'asc'
  }
}

const sortedAndFilteredDocuments = computed(() => {
  let arr = documents.value.filter((doc) =>
    doc.document_name.toLowerCase().includes(search.value.toLowerCase()),
  )
  let min = 0,
    max = 100
  const match = filterRange.value.match(/^(\d+)(?:\s*-\s*(\d+))?$/)
  if (match) {
    min = parseInt(match[1], 10)
    max = match[2] ? parseInt(match[2], 10) : min
  }
  arr = arr.filter((doc) => {
    const prob = Math.round(getMainClassification(doc).score * 100)
    return prob >= min && prob <= max
  })

  if (sortBy.value === 'document') {
    arr.sort((a, b) =>
      sortDir.value === 'asc'
        ? a.document_name.localeCompare(b.document_name)
        : b.document_name.localeCompare(a.document_name),
    )
  } else {
    arr.sort((a, b) => {
      const pa = getMainClassification(a).score
      const pb = getMainClassification(b).score
      return sortDir.value === 'asc' ? pa - pb : pb - pa
    })
  }
  return arr
})

function getMainClassification(doc: DocumentClassifications) {
  return doc.classifications.reduce(
    (max, c) => (c.score > max.score ? c : max),
    doc.classifications[0],
  )
}

async function updateMainClassification(doc: DocumentClassifications, newLabel: string) {
  const selected = doc.classifications.find((c) => c.label === newLabel)
  if (!selected) return

  const main = getMainClassification(doc)
  if (main.label === newLabel) return

  const temp = main.score
  main.score = selected.score
  selected.score = temp

  doc.edited = true

  mainClassificationLabel.value[doc.id] = newLabel
  editingDocId.value = null //unselect after change

  const payload = {
    document_name: doc.document_name,
    classifications: doc.classifications.map((c) => ({ label: c.label, score: c.score })),
  }
  await fetch('http://localhost:8080/classifications/' + doc.id, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
}

defineOptions({ name: 'DocumentClassificationsTable' })
</script>

<style>
body,
.classifications-outer {
  background: #f7f9fb;
}
.classifications-outer {
  max-width: 600px;
  margin: 40px auto;
}
.classifications-container {
  width: 100%;
  background: transparent;
  padding: 32px 0 0 0;
  border-radius: 12px;
}
.controls-row {
  display: flex;
  align-items: center;
  gap: 18px;
  margin: 0 0 24px 0;
}
h2 {
  font-size: 2rem;
  font-weight: 700;
  margin: 0 0 0 0;
}
table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 18px;
  background: transparent;
}
thead th {
  text-align: left;
  color: #888;
  font-size: 1rem;
  font-weight: 600;
  padding: 0 24px 8px 24px;
  background: transparent;
}
tbody tr {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.03);
  font-size: 1.08rem;
  font-weight: 500;
  transition: box-shadow 0.2s;
}
tbody tr:hover {
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}
td {
  padding: 18px 24px;
  border: none;
  background: transparent;
}
td:first-child {
  font-weight: 700;
  color: #222;
}
td:last-child {
  text-align: right;
  font-weight: 600;
  color: #222;
}
.edited-badge {
  display: inline-block;
  margin-left: 8px;
  padding: 2px 6px;
  background: #e0c3fc;
  color: #6c2eb5;
  border-radius: 6px;
  font-size: 0.85em;
  font-style: italic;
}
/* Purple arrow for sorted column */
thead th span[style*='color: purple'] {
  color: #8f5cf7 !important;
}
.styled-select {
  padding: 8px 12px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
  background: #fff;
  font-size: 1rem;
  font-weight: 500;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.03);
  outline: none;
  transition: border 0.2s;
}
.styled-select:focus {
  border: 1.5px solid #8f5cf7;
}
</style>
