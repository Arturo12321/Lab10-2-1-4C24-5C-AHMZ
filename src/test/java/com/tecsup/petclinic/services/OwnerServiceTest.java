package com.tecsup.petclinic.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
public class OwnerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);

	@Autowired
	private OwnerService ownerService;

	/**
	 * 
	 */
	@Test
	public void testFindOwnerById() {

		long ID = 1;
		String NAME = "Leo";
		Owner owner = null;
		
		try {
			owner = ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		
		logger.info("" + owner);
		assertThat(owner.getName(), is(NAME));

	}

	/**
	 * 
	 */
	@Test
	public void testFindOwnerByName() {

		String FIND_NAME = "Leo";
		int SIZE_EXPECTED = 1;

		List<Owner> owners = ownerService.findByName(FIND_NAME);

		assertThat(owners.size(), is(SIZE_EXPECTED));
	}

	/**
	 * 
	 */
	@Test
	public void testFindOwnerByTypeId() {

		int TYPE_ID = 5;
		int SIZE_EXPECTED = 2;

		List<Owner> owners = ownerService.findByTypeId(TYPE_ID);

		assertThat(owners.size(), is(SIZE_EXPECTED));
	}

	/**
	 * 
	 */
	@Test
	public void testFindOwnerByOwnerId() {

		int OWNER_ID = 10;
		int SIZE_EXPECTED = 2;

		List<Owner> owners = ownerService.findByOwnerId(OWNER_ID);

		assertThat(owners.size(), is(SIZE_EXPECTED));
		
	}

	/**
	 *  To get ID generate , you need 
	 *  setup in id primary key in your
	 *  entity this annotation :
	 *  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 */
	@Test
	public void testCreateOwner() {

		String FIRST_NAME = "Aracely";
		String LAST_NAME = "Huamani";
		String ADDRESS = " Av Los Pinos 321";
		String CITY = "Lima";
		int TELEPHONE = 998954673;

		Owner owner = new Owner(FIRST_NAME,LAST_NAME,ADDRESS,CITY,998954673);
		
		Owner ownerCreated = ownerService.create(owner);
		
		logger.info("Owner CREATED :" + ownerCreated);

		//          ACTUAL                 , EXPECTED 
		assertThat(ownerCreated.getId()      , notNullValue());
		assertThat(ownerCreated.getFirst_name()    , is(FIRST_NAME));
		assertThat(ownerCreated.getLast_name() , is(LAST_NAME));
		assertThat(ownerCreated.getAddress()  , is(ADDRESS));
		assertThat(ownerCreated.getCity()  , is(CITY));
		assertThat(ownerCreated.getTelephone()  , is(TELEPHONE));

	}

	
	/**
	 * 
	 */
	@Test
	public void testUpdateOwner() {

		String OWNER_NAME = "Bear";
		int OWNER_ID = 1;
		int TYPE_ID = 1;
		long create_id = -1;

		String UP_OWNER_NAME = "Bear2";
		int UP_OWNER_ID = 2;
		int UP_TYPE_ID = 2;

		Owner owner = new Owner(OWNER_NAME, OWNER_ID, TYPE_ID);

		// Create record
		logger.info(">" + owner);
		Owner ownerCreated = ownerService.create(owner);
		logger.info(">>" + ownerCreated);

		create_id = ownerCreated.getId();

		// Prepare data for update
		ownerCreated.setName(UP_OWNER_NAME);
		ownerCreated.setOwnerId(UP_OWNER_ID);
		ownerCreated.setTypeId(UP_TYPE_ID);

		// Execute update
		Owner upgradeOwner = ownerService.update(ownerCreated);
		logger.info(">>>>" + upgradeOwner);

		//        ACTUAL       EXPECTED
		assertThat(create_id ,notNullValue());
		assertThat(upgradeOwner.getId(), is(create_id));
		assertThat(upgradeOwner.getName(), is(UP_OWNER_NAME));
		assertThat(upgradeOwner.getTypeId(), is(UP_OWNER_ID));
		assertThat(upgradeOwner.getOwnerId(), is(UP_TYPE_ID));
	}

	/**
	 * 
	 */
	@Test
	public void testDeleteOwner() {

		String OWNER_NAME = "Bird";
		int OWNER_ID = 1;
		int TYPE_ID = 1;

		Owner owner = new Owner(OWNER_NAME, OWNER_ID, TYPE_ID);
		owner = ownerService.create(owner);
		logger.info("" + owner);

		try {
			ownerService.delete(owner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
			
		try {
			ownerService.findById(owner.getId());
			fail("Owner id = " + owner.getId() + " has not delete");
		} catch (OwnerNotFoundException e) {
		} 				

	}
	
}
