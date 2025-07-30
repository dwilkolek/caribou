
-- File: models/project_summary.sql

SELECT p.project_id,
       p.name AS project_name,
       d.department_name
FROM {{ source('raw', 'projects') }} p
JOIN {{ ref('department_summary') }} d ON p.owner_id IN (
    SELECT employee_id FROM {{ ref('source_employees') }} WHERE department_id = d.department_id
)
