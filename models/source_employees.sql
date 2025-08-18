SELECT *
FROM {{ source('raw', 'employees') }}
