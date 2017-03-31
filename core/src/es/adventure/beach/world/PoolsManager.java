package es.adventure.beach.world;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.Airplane;
import es.adventure.beach.entity.B2;
import es.adventure.beach.entity.Bomb;
import es.adventure.beach.entity.Drone;
import es.adventure.beach.entity.Nube;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.weapons.Escopeta;
import es.adventure.beach.weapons.Mp5;
import es.adventure.beach.weapons.Pistola;
import es.adventure.beach.weapons.Rocket;
import es.adventure.beach.weapons.RoundBullet;

public class PoolsManager {
	private GameScreen screen;

	public PoolsManager(GameScreen screen) {
		this.screen = screen;
		Pools.set(Pistola.class, pistolPool);
		Pools.set(Escopeta.class, escopetaPool);
		Pools.set(Mp5.class, mp5Pool);
		Pools.set(RoundBullet.class, rbulletPool);
		Pools.set(Airplane.class, airplanePool);
		Pools.set(B2.class, b2Pool);
		Pools.set(Drone.class, dronePool);
		Pools.set(Bomb.class, bombPool);
		Pools.set(Nube.class, nubePool);
		Pools.set(Rocket.class, rocketPool);
		

	}

	private Pool<Pistola> pistolPool = new Pool<Pistola>(8) {
		@Override
		protected Pistola newObject() {
			return new Pistola(screen, screen.getPlayer().getB2body()
					.getPosition().x, screen.getPlayer().getB2body()
					.getPosition().y, screen.getPlayer().getCurrentBeta(),screen.getPistolArsenal());
		}
	};

	private Pool<Escopeta> escopetaPool = new Pool<Escopeta>(20) {
		@Override
		protected Escopeta newObject() {
			return new Escopeta(screen, screen.getPlayer().getB2body()
					.getPosition().x, screen.getPlayer().getB2body()
					.getPosition().y, screen.getPlayer().getCurrentBeta(),screen.getEscopetaArsenal());
		}
	};

	private Pool<Mp5> mp5Pool = new Pool<Mp5>(20) {
		@Override
		protected Mp5 newObject() {
			return new Mp5(screen,
					screen.getPlayer().getB2body().getPosition().x, screen
							.getPlayer().getB2body().getPosition().y, screen
							.getPlayer().getCurrentBeta(),screen.getMp5Arsenal());
		}
	};
	private Pool<RoundBullet> rbulletPool = new Pool<RoundBullet>(20) {
		@Override
		protected RoundBullet newObject() {
			return new RoundBullet(screen, screen.getPlayer().getB2body()
					.getPosition().x, screen.getPlayer().getB2body()
					.getPosition().y, screen.getPlayer().getCurrentBeta(),screen.getRocketArsenal());
		}
	};
	private Pool<Rocket> rocketPool = new Pool<Rocket>(2) {
		@Override
		protected Rocket newObject() {
			return new Rocket(screen, screen.getPlayer().getB2body()
					.getPosition().x, screen.getPlayer().getB2body()
					.getPosition().y, screen
					.getPlayer().getCurrentBeta(),screen.getRocketArsenal());
		}
	};
	private Pool<Airplane> airplanePool = new Pool<Airplane>(8) {
		@Override
		protected Airplane newObject() {
			return new Airplane(screen, "BIASED");
		}
	};

	private Pool<B2> b2Pool = new Pool<B2>(3) {
		@Override
		protected B2 newObject() {
			return new B2(screen);
		}
	};

	private Pool<Drone> dronePool = new Pool<Drone>(10) {
		@Override
		protected Drone newObject() {
			return new Drone(screen);
		}
	};

	private Pool<Bomb> bombPool = new Pool<Bomb>(3) {
		@Override
		protected Bomb newObject() {
			return new Bomb(screen);
		}
	};

	private Pool<Nube> nubePool = new Pool<Nube>(2) {
		@Override
		protected Nube newObject() {
			return new Nube(screen);
		}
	};
	

}