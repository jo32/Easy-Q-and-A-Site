package info.jo32.EasyQandASite.persistence;

import java.sql.ResultSet;
import java.util.List;

import info.jo32.EasyQandASite.controller.Entity;

public abstract class EntityWrapper {

	Entity entity;

	public EntityWrapper(Entity e) {
		this.entity = e;
	}

	public abstract String getInsertStatement();

	public abstract String getDeleteStatement();

	public abstract String getSelectStatement(String predicate);

	public abstract List<Entity> getEntityFromResultSet(ResultSet resultSet);

	public abstract String getModifyStatement();

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
