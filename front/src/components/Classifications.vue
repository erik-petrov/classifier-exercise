<template>
  <div class="classifications-container">
    <h2>Classification List</h2>
    <div class="controls-row">
      <select v-model="sortBy">
        <option value="name">Name</option>
        <option value="probability">Probability</option>
      </select>
      <span>Higher than</span>
      <select v-model="filterBy">
        <option value="probability">Probability</option>
      </select>
    </div>
    <div class="table-header">
      <span class="header-classification" @click="sort('name')">
        Classification
        <span v-if="sortBy === 'name'">↑</span>
      </span>
      <span class="header-probability" @click="sort('probability')">
        Probability
        <span v-if="sortBy === 'probability'">↓</span>
      </span>
    </div>
    <div v-for="item in sortedClassifications" :key="item.name" class="classification-row">
      <span class="classification-name">{{ item.name }}</span>
      <span class="classification-probability">{{ item.probability }}%</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
defineOptions({ name: 'ClassificationList' })

const classifications = ref([
  { name: 'Classification 1', probability: 99 },
  { name: 'Classification 2', probability: 7 },
])

const sortBy = ref<'name' | 'probability'>('name')
const filterBy = ref<'probability'>('probability')

function sort(field: 'name' | 'probability') {
  sortBy.value = field
}

const sortedClassifications = computed(() => {
  const arr = [...classifications.value]
  if (sortBy.value === 'name') {
    arr.sort((a, b) => a.name.localeCompare(b.name))
  } else {
    arr.sort((a, b) => b.probability - a.probability)
  }
  return arr
})
</script>

<style scoped>
.classifications-container {
  max-width: 500px;
  margin: 40px auto;
  background: #f8f9fb;
  padding: 32px 0 0 0;
  border-radius: 12px;
}
h2 {
  margin-left: 16px;
  font-size: 1.5rem;
  font-weight: 700;
}
.controls-row {
  display: flex;
  align-items: center;
  gap: 12px;
  margin: 24px 0 16px 16px;
}
select {
  padding: 4px 12px;
  border-radius: 6px;
  border: 1px solid #e0e0e0;
  background: #fff;
  font-size: 1rem;
}
.table-header {
  display: flex;
  justify-content: space-between;
  padding: 0 24px;
  color: #888;
  font-size: 0.95rem;
  font-weight: 600;
  margin-bottom: 8px;
}
.header-classification,
.header-probability {
  cursor: pointer;
  user-select: none;
}
.classification-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  margin: 8px 16px;
  padding: 18px 24px;
  border-radius: 10px;
  font-size: 1.08rem;
  font-weight: 500;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.03);
}
.classification-name {
  color: #222;
}
.classification-probability {
  color: #222;
  font-weight: 600;
}
</style>
