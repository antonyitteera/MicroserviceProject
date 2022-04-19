INSERT INTO smartcard_details(
	 cardid,balance, smart_card_num, card_status, date_of_exp, date_of_reg)
	VALUES (1,100.00, '1111-2222-3333', 'Available', '19-04-2023', '19-04-2022');
	
INSERT INTO smartcard_details(
	 cardid,balance, smart_card_num, card_status, date_of_exp, date_of_reg)
	VALUES (2,50.00, '1123-2222-3333', 'Available', '04-04-2023', '04-04-2022');

INSERT INTO user_details(
	user_id,age_group, date_of_birth, email_id, first_name, mobile_number, pin, second_name, smartcard_details_cardid, role)
	VALUES (1,'Adult', '28-09-1995', 'antonyitteera@gmail.com', 'Antony', '8111802071', '1234', 'Itteera', 1, 'admin');
	
INSERT INTO user_details(
	user_id,age_group, date_of_birth, email_id, first_name, mobile_number, pin, second_name, smartcard_details_cardid, role)
	VALUES (2,'Adult', '15-03-1995', 'amlalx@gmail.com', 'Amal', '8111801163', '4321', 'Alex', 2, 'user');
	