WITH RECURSIVE
    days(day) AS (
        SELECT '2015-10-05T00:00:00Z'
        UNION ALL
        SELECT datetime(day,'utc','+1 day') FROM days WHERE day <= '2015-11-21T00:00:00Z'
    )
SELECT day FROM days;
