SELECT setval(
               pg_get_serial_sequence('categories', 'id'),
               (SELECT MAX(id) FROM categories)
       );