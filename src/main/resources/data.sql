-- some test users

INSERT INTO roles (id, role)
VALUES
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO users (id, email, first_name, last_name,  password, score)
VALUES
    (1, 'admin@example.com', 'Admin', 'Adminov', 'defaultPass', 400);
    (2, 'userOne@example.com', 'User', 'NumberOne', 'defaultPass', 900);
    (3, 'userOne@example.com', 'User', 'NumberTwo', 'defaultPass', 150);
    (4, 'userOne@example.com', 'User', 'NumberThree', 'defaultPass', 550);


INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1);


INSERT INTO dictionary (id, word, definition)
VALUES (1, 'баница', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio'),
       (2, 'слушалки', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio'),
       (3, 'панталон', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio'),
       (4, 'ландшафт', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio');

