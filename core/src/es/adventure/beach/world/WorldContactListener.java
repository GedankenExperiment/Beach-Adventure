package es.adventure.beach.world;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import es.adventure.beach.entity.Bomb;
import es.adventure.beach.entity.Drone;
import es.adventure.beach.entity.Enemy;
import es.adventure.beach.entity.Entity;
import es.adventure.beach.entity.IntedPowUp;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.sprites.Drone1;
import es.adventure.beach.tiledobjects.Computer;
import es.adventure.beach.weapons.Weapon;

public class WorldContactListener implements ContactListener {
	private GameScreen screen;

	public WorldContactListener(GameScreen gameScreen) {
		this.screen = gameScreen;
	}

	@Override
	public void beginContact(Contact contact) {
		// Gdx.app.log("Begin Contact", "");
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		// /colision definition
		int cDef = fixA.getFilterData().categoryBits
				| fixB.getFilterData().categoryBits;

		// if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
		// Fixture body = fixA.getUserData() == "head" ? fixA : fixB;
		// Fixture object = body == fixA ? fixB : fixA;
		// if (body.getUserData() instanceof Drone) {
		// // ((InteractiveTileObject) object.getUserData()).tileHit();
		// Gdx.app.log("head collided with", "brick");
		//
		// }
		// if (object.getUserData() !=null &&
		// Brick.class.isAssignableFrom(object.getClass())) {
		// // ((InteractiveTileObject) object.getUserData()).tileHit();
		//
		// Gdx.app.log("head collided with", "brick");
		// }
		// if (object.getUserData() instanceof Nube) {
		// // ((InteractiveTileObject) object.getUserData()).tileHit();
		//
		// Gdx.app.log("head collided with", "nube");
		// }
		// }

		switch (cDef) {

		// WEAPONS VS GROUND
		case BeachAdventure.BOMB_BIT | BeachAdventure.GROUND_BIT://
			if (fixA.getFilterData().categoryBits == BeachAdventure.GROUND_BIT) {
				((Bomb) fixB.getUserData()).hitByGround();

			} else {
				// ((Pistola) fixA.getUserData()).hitByGround();
				((Bomb) fixA.getUserData()).hitByGround();
			}
			break;
		case BeachAdventure.BOMB_BIT | BeachAdventure.PLAYER_BIT://
			if (fixA.getFilterData().categoryBits == BeachAdventure.PLAYER_BIT) {
				((Bomb) fixB.getUserData()).hitByPlayer();
				;

			} else {

				((Bomb) fixA.getUserData()).hitByPlayer();

			}
			break;
		case BeachAdventure.INTED_ARTIFACT_BIT | BeachAdventure.PLAYER_BIT://
			if (fixA.getFilterData().categoryBits == BeachAdventure.PLAYER_BIT) {
				((IntedPowUp) fixB.getUserData()).intedSwitch();
				;

			} else {

				((IntedPowUp) fixA.getUserData()).intedSwitch();
				;
			}
			break;

	

	
		// WEAPONS CAN HIT ON GROUND, AIRPLANES, B2 and cant collide eachother
		case BeachAdventure.WEAPONS_BIT | BeachAdventure.ENEMY_BIT://
			if (fixA.getFilterData().categoryBits == BeachAdventure.WEAPONS_BIT) {
				((Weapon) fixA.getUserData()).hitByEnemy();
				
				((Enemy) fixB.getUserData()).receiveDamage(((Weapon) fixA
						.getUserData()).WEAPON_CODE);
			} else {
				((Weapon) fixB.getUserData()).hitByEnemy();
				((Enemy) fixA.getUserData()).receiveDamage(((Weapon) fixB
						.getUserData()).WEAPON_CODE);
			}
			break;
		case BeachAdventure.ENEMY_BIT | BeachAdventure.GROUND_BIT://
			if (fixA.getFilterData().categoryBits == BeachAdventure.ENEMY_BIT) {
				
				((Enemy) fixA.getUserData()).hitByGround();
			} else {

				((Enemy) fixB.getUserData()).hitByGround();
			}
			break;
		case BeachAdventure.COMPUTER_BIT | BeachAdventure.DRONE1_BIT://
			if (fixA.getFilterData().categoryBits == BeachAdventure.DRONE1_BIT) {
				if (!((Drone1) fixA.getUserData()).checkData()) {
					screen.getDrone1().loadFromComputer();
					((Computer) fixB.getUserData()).tileHit();
				}
			} else {
				if (!((Drone1) fixB.getUserData()).checkData()) {
					screen.getDrone1().loadFromComputer();
					((Computer) fixA.getUserData()).tileHit();
				}
			}
			break;
	
		}
	}

	@Override
	public void endContact(Contact contact) {
		// Gdx.app.log("End Contact", "");

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	public static void droneIA(Fixture fixA) {
		if ((fixA.getUserData() == "head") || (fixA.getUserData() == "bottom")) {

			// Gdx.app.log("collided with", "head");
			fixA.getBody().setLinearVelocity(
					fixA.getBody().getLinearVelocity().x,
					-fixA.getBody().getLinearVelocity().y);
			// break;

		} else if ((fixA.getUserData() == "left")
				|| (fixA.getUserData() == "right")) {
			fixA.getBody().setLinearVelocity(
					-fixA.getBody().getLinearVelocity().x,
					fixA.getBody().getLinearVelocity().y);
		}

	}
}
