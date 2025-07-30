
-- File: models/task_hierarchy.sql

SELECT t.task_id,
       t.name AS task_name,
       t.status,
       p.project_name,
       d.department_name
FROM {{ source('raw', 'tasks') }} t
JOIN {{ ref('project_summary') }} p ON t.project_id = p.project_id
JOIN {{ ref('department_summary') }} d ON p.department_name = d.department_name
