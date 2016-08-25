CREATE TABLE locations(
	location_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(30) 
);

CREATE TABLE images (
image_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY PRIMARY KEY,
image1 BLOB,
image2 BLOB,
image3 BLOB,
image4 BLOB,
image5 BLOB
);

CREATE TABLE items (
item_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
image_id INT NULL, 
hours INT NULL,
displacement INT NULL,
color VARCHAR(15) NULL, 
category VARCHAR(20) NULL ,
seating VARCHAR(20) NULL ,
vinNo VARCHAR(17) NULL ,
engine VARCHAR(20) NULL,
pumpType VARCHAR(20) NULL, 
fuelCap INT NULL, 
FOREIGN KEY (image_id) REFERENCES images (image_id)
);

CREATE TABLE profiles (
profile_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
phone_number VARCHAR(15) NULL,
mobile_number VARCHAR(15) NULL
);

CREATE TABLE itemOfferings (
Item_offering_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
brand VARCHAR(15) NOT NULL, 
model VARCHAR(15) NOT NULL, 
year INT NOT NULL,
cost INT NOT NULL,
location_id INT NOT NULL,
descript VARCHAR(100),
user_id INT NOT NULL, 
FOREIGN KEY ( item_offering_id ) REFERENCES items (item_id), 
FOREIGN KEY (location_id) REFERENCES locations (location_id) 
);	

CREATE TABLE users (
user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_name VARCHAR(20) NOT NULL,
user_password VARCHAR(15) NOT NULL,
user_role VARCHAR(15) NULL,
foreign key (user_id) references profiles(profile_id)
);

CREATE TABLE accounts (
user_id INT NOT NULL,
item_offering_id INT NOT NULL,
primary key (user_id, item_offering_id),
FOREIGN KEY (user_id) REFERENCES users (user_id),
FOREIGN KEY (item_offering_id) REFERENCES itemofferings (item_offering_id)  
);
