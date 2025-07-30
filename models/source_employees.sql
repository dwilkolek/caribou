
-- File: models/source_employees.sql

SELECT *
FROM {{ source('raw', 'employees') }}
