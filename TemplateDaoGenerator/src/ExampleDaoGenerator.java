/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {

	public static void main(String[] args) {
		Schema schema = new Schema(1, "com.trs.template.dao.greendao");

		addTable(schema);

		try {

			new DaoGenerator().generateAll(schema, "./greendao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addTable(Schema schema) {
		// user
		Entity userEntity = schema.addEntity("User");
		userEntity.addIdProperty().autoincrement();
		userEntity.addStringProperty("tel").notNull().unique();
		userEntity.addStringProperty("name").notNull();
		userEntity.addStringProperty("password").notNull();
		userEntity.addDateProperty("create_time");
		userEntity.addDateProperty("last_login_time");
		// beacon
		Entity beaconEntity = schema.addEntity("Beacon");
		beaconEntity.addIdProperty().autoincrement();
		beaconEntity.addStringProperty("uuid").notNull();
		beaconEntity.addIntProperty("major").notNull();
		beaconEntity.addIntProperty("minor").notNull();
		beaconEntity.addDateProperty("time");
		beaconEntity.addDoubleProperty("temperature");
		beaconEntity.addDoubleProperty("light");
		beaconEntity.addDoubleProperty("lon");
		beaconEntity.addDoubleProperty("lat");

		Property user_id = beaconEntity.addLongProperty("user_id").getProperty();
		// one beacon to one user
		beaconEntity.addToOne(userEntity, user_id);
		// one user to many beacons
		userEntity.addToMany(beaconEntity, user_id);
	}

	private static void addNote(Schema schema) {
		Entity note = schema.addEntity("Note");
		note.addIdProperty();
		note.addStringProperty("text").notNull();
		note.addStringProperty("comment");
		note.addDateProperty("date");
	}

	private static void addCustomerOrder(Schema schema) {
		Entity customer = schema.addEntity("Customer");
		customer.addIdProperty();
		customer.addStringProperty("name").notNull();

		Entity order = schema.addEntity("Order");
		order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
		order.addIdProperty();
		Property orderDate = order.addDateProperty("date").getProperty();
		Property customerId = order.addLongProperty("customerId").notNull().getProperty();
		order.addToOne(customer, customerId);

		ToMany customerToOrders = customer.addToMany(order, customerId);
		customerToOrders.setName("orders");
		customerToOrders.orderAsc(orderDate);
	}

}
