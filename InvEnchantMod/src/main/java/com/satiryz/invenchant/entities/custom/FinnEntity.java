package com.satiryz.invenchant.entities.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FinnEntity extends Wolf {

	public FinnEntity(EntityType<? extends Wolf> entityType, Level level) {
		super(entityType, level);
		this.setTame(true);
	}
	
	public static AttributeSupplier setAttributes() {
		return Wolf.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 160D)
				.add(Attributes.ATTACK_DAMAGE, 2.0f)
				.add(Attributes.ATTACK_SPEED, 1.0f)
				.add(Attributes.MOVEMENT_SPEED, 0.3f)
				.build();
	}
	
	@Override
	protected void registerGoals() {
	      this.goalSelector.addGoal(1, new FloatGoal(this));
	      this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
	      this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
	      this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
	      this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
	      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
	      this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
	      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
	      this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
	      this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
	      this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
	      this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
	      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
	      this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
	   }
}
