INSERT INTO document (name) VALUES
                                ('Clinical Summary'),
                                ('Invoice April'),
                                ('Patient Consent'),
                                ('Lab Results'),
                                ('Contract Draft'),
                                ('Email Chain with Sponsor'),
                                ('Monitoring Visit Report'),
                                ('Budget Proposal'),
                                ('Protocol Amendment'),
                                ('Internal Memo');

INSERT INTO classification (label)
SELECT label FROM (VALUES
                       ('Medical'), ('Regulatory'), ('Correspondence'), ('Financial'), ('Legal'),
                       ('Miscellaneous'), ('Administrative'), ('Laboratory'), ('Other'),
                       ('Business'), ('Monitoring')
                  ) AS t(label)
WHERE NOT EXISTS (SELECT 1 FROM classification WHERE classification.label = t.label);

INSERT INTO score (document_id, classification_id, score)
VALUES

((SELECT id FROM document WHERE name = 'Clinical Summary'),
 (SELECT id FROM classification WHERE label = 'Medical'), 0.87),
((SELECT id FROM document WHERE name = 'Clinical Summary'),
 (SELECT id FROM classification WHERE label = 'Regulatory'), 0.08),
((SELECT id FROM document WHERE name = 'Clinical Summary'),
 (SELECT id FROM classification WHERE label = 'Correspondence'), 0.05),

((SELECT id FROM document WHERE name = 'Invoice April'),
 (SELECT id FROM classification WHERE label = 'Financial'), 0.91),
((SELECT id FROM document WHERE name = 'Invoice April'),
 (SELECT id FROM classification WHERE label = 'Legal'), 0.06),
((SELECT id FROM document WHERE name = 'Invoice April'),
 (SELECT id FROM classification WHERE label = 'Miscellaneous'), 0.03),

((SELECT id FROM document WHERE name = 'Patient Consent'),
 (SELECT id FROM classification WHERE label = 'Legal'), 0.76),
((SELECT id FROM document WHERE name = 'Patient Consent'),
 (SELECT id FROM classification WHERE label = 'Medical'), 0.19),
((SELECT id FROM document WHERE name = 'Patient Consent'),
 (SELECT id FROM classification WHERE label = 'Administrative'), 0.05),

((SELECT id FROM document WHERE name = 'Lab Results'),
 (SELECT id FROM classification WHERE label = 'Medical'), 0.94),
((SELECT id FROM document WHERE name = 'Lab Results'),
 (SELECT id FROM classification WHERE label = 'Laboratory'), 0.03),
((SELECT id FROM document WHERE name = 'Lab Results'),
 (SELECT id FROM classification WHERE label = 'Other'), 0.03),

((SELECT id FROM document WHERE name = 'Contract Draft'),
 (SELECT id FROM classification WHERE label = 'Legal'), 0.81),
((SELECT id FROM document WHERE name = 'Contract Draft'),
 (SELECT id FROM classification WHERE label = 'Business'), 0.14),
((SELECT id FROM document WHERE name = 'Contract Draft'),
 (SELECT id FROM classification WHERE label = 'Financial'), 0.05),

((SELECT id FROM document WHERE name = 'Email Chain with Sponsor'),
 (SELECT id FROM classification WHERE label = 'Correspondence'), 0.83),
((SELECT id FROM document WHERE name = 'Email Chain with Sponsor'),
 (SELECT id FROM classification WHERE label = 'Administrative'), 0.1),
((SELECT id FROM document WHERE name = 'Email Chain with Sponsor'),
 (SELECT id FROM classification WHERE label = 'Legal'), 0.07),

((SELECT id FROM document WHERE name = 'Monitoring Visit Report'),
 (SELECT id FROM classification WHERE label = 'Monitoring'), 0.89),
((SELECT id FROM document WHERE name = 'Monitoring Visit Report'),
 (SELECT id FROM classification WHERE label = 'Regulatory'), 0.07),
((SELECT id FROM document WHERE name = 'Monitoring Visit Report'),
 (SELECT id FROM classification WHERE label = 'Medical'), 0.04),

((SELECT id FROM document WHERE name = 'Budget Proposal'),
 (SELECT id FROM classification WHERE label = 'Financial'), 0.86),
((SELECT id FROM document WHERE name = 'Budget Proposal'),
 (SELECT id FROM classification WHERE label = 'Administrative'), 0.09),
((SELECT id FROM document WHERE name = 'Budget Proposal'),
 (SELECT id FROM classification WHERE label = 'Legal'), 0.05),

((SELECT id FROM document WHERE name = 'Protocol Amendment'),
 (SELECT id FROM classification WHERE label = 'Regulatory'), 0.74),
((SELECT id FROM document WHERE name = 'Protocol Amendment'),
 (SELECT id FROM classification WHERE label = 'Medical'), 0.2),
((SELECT id FROM document WHERE name = 'Protocol Amendment'),
 (SELECT id FROM classification WHERE label = 'Legal'), 0.06),

((SELECT id FROM document WHERE name = 'Internal Memo'),
 (SELECT id FROM classification WHERE label = 'Administrative'), 0.77),
((SELECT id FROM document WHERE name = 'Internal Memo'),
 (SELECT id FROM classification WHERE label = 'Correspondence'), 0.18),
((SELECT id FROM document WHERE name = 'Internal Memo'),
 (SELECT id FROM classification WHERE label = 'Other'), 0.05);