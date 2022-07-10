package com.egesaylan.adventurebird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class AdventureBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture rocket1;
	Texture rocket2;
	Texture rocket3;
	float birdx;
	float birdy;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.4f;
	float rocketVelocity = 10;
	Random random;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredRocket =0;
	BitmapFont font;
	BitmapFont font2;

	Circle birdcircle;




	int numberofrockets = 4;
	float [] rocketx = new float[numberofrockets];
	float [] rocketoffset  = new float[numberofrockets];
	float [] rocketoffset2 = new float[numberofrockets];
	float [] rocketoffset3 = new float[numberofrockets];

	Circle[] rocketcircles;
	Circle[] rocketcircles2;
	Circle[] rocketcircles3;



	float distance = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("hero.png");
		rocket1 = new Texture("enemy.png");
		rocket2 = new Texture("enemy.png");
		rocket3 = new Texture("enemy.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


		birdx = Gdx.graphics.getWidth()/4;
		birdy = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer();

		birdcircle = new Circle();
		rocketcircles  = new Circle[numberofrockets];
		rocketcircles2 = new Circle[numberofrockets];
		rocketcircles3 = new Circle[numberofrockets];

		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.GOLD);
		font2.getData().setScale(5);



		for (int i = 0; i < numberofrockets; i++ ){

			rocketoffset[i]  = (random.nextFloat()) * (Gdx.graphics.getHeight()- 200) ;
			rocketoffset2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight()- 200) ;
			rocketoffset3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight()- 200) ;



			rocketx[i] = Gdx.graphics.getWidth() - rocket1.getWidth() /2 + i * distance;

			rocketcircles [i] = new Circle();
			rocketcircles2[i] = new Circle();
			rocketcircles3[i] = new Circle();
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1){

			if(rocketx[scoredRocket] < Gdx.graphics.getWidth()/4){
				score++;

				if(scoredRocket < numberofrockets-1){
					scoredRocket++;
				}else{
					scoredRocket=0;
				}
			}

			if(Gdx.input.justTouched()){

				velocity = velocity -14;

			}


			for(int i =0; i< numberofrockets; i++){

				if(rocketx[i] < Gdx.graphics.getWidth()/15){
					rocketx[i] = rocketx[i] + numberofrockets *distance;

					rocketoffset [i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()- 200) ;
					rocketoffset2[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()- 200) ;
					rocketoffset3[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()- 200) ;

				}else{
					rocketx[i] = rocketx[i] - rocketVelocity;
				}



				batch.draw(rocket1,rocketx[i],Gdx.graphics.getHeight()/2 + rocketoffset [i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(rocket2,rocketx[i],Gdx.graphics.getHeight()/2 + rocketoffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(rocket3,rocketx[i],Gdx.graphics.getHeight()/2 + rocketoffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				rocketcircles [i] = new Circle(rocketx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + rocketoffset [i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				rocketcircles2[i] = new Circle(rocketx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + rocketoffset2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				rocketcircles3[i] = new Circle(rocketx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + rocketoffset3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			}

			if(birdy > 0){
				velocity = velocity + gravity;
				birdy = birdy - velocity;
			}else{
				gameState =2;
			}
		}else if(gameState ==0){
			if(Gdx.input.justTouched()){
				gameState = 1;

			}

		}else if(gameState == 2){
			font2.draw(batch,"Game Over!",Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2);

			if(Gdx.input.justTouched()){
				gameState = 1;

				birdy = Gdx.graphics.getHeight()/2;

				for (int i = 0; i < numberofrockets; i++ ){

					rocketoffset[i]  = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()- 200) ;
					rocketoffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()- 200) ;
					rocketoffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()- 200) ;



					rocketx[i] = Gdx.graphics.getWidth() - rocket1.getWidth() /2 + i * distance;

					rocketcircles [i] = new Circle();
					rocketcircles2[i] = new Circle();
					rocketcircles3[i] = new Circle();
				}
				velocity = 0;
				scoredRocket =0;
				score =0;
			}
		}


		batch.draw(bird,birdx,birdy,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		birdcircle.set(birdx +Gdx.graphics.getWidth() /30,birdy+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth() /30);


		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdcircle.x,birdcircle.y,birdcircle.radius);



		for(int i =0; i<numberofrockets; i++){
			//shapeRenderer.circle(rocketx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + rocketoffset [i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(rocketx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + rocketoffset2 [i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(rocketx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + rocketoffset3 [i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			if(Intersector.overlaps(birdcircle,rocketcircles[i])|| Intersector.overlaps(birdcircle,rocketcircles2[i])|| Intersector.overlaps(birdcircle,rocketcircles3[i])){
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
