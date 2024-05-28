select type,
SELECT GROUP_CONCAT(DISTINCT CONCAT(
        'MAX(CASE WHEN product = ''',
        v.product,
        ''' THEN price ELSE 0 END) AS `',
        v.product,
        '`'
    ))
FROM vip_price v from
		vip_price vp
group by vp.type