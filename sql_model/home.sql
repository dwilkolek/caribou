SELECT * from rpl_home h
LEFT JOIN {{ ref('people') }} p on p.home_id = h.id