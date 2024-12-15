insert into
    public.client ("name")
values
    ('Иван'),
    ('Ольга'),
    ('Наталья'),
    ('Евгений'),
    ('Олег');

insert into
    public.address (street,client_id)
values
    ('Ленинский проспект 45',1),
    ('Фомичевой 6',2),
    ('Фомичевой 6',3),
    ('Древлянка 22',4),
    ('Тверская 8',5);


insert into
    public.phone ("number",client_id)
values
    ('+79115559988',1),
    ('+79876543212',3),
    ('+79996542313',4),
    ('+79115559988',5),
    ('+79876543212',1);

ALTER TABLE address ADD CONSTRAINT fk_address FOREIGN KEY (client_id) REFERENCES client(id);
ALTER TABLE phone ADD CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(id);