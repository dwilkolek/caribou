
-- File: models/department_summary.sql

SELECT d.department_id,
       d.name AS department_name,
       COUNT(e.employee_id) AS num_employees
FROM {{ ref('source_employees') }} e
JOIN {{ source('raw', 'departments') }} d ON e.department_id = d.department_id
GROUP BY d.department_id, d.name
