package com.greentree.engine.moon.render.ui;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record RectTransform(Vector2f min, Vector2f max, float rotation) implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	
	public RectTransform(float x1, float y1, float x2, float y2, float rotation) {
		this(new Vector2f(Mathf.min(x1, x2), Mathf.min(y1, y2)),
				new Vector2f(Mathf.max(x1, x2), Mathf.max(y1, y2)), rotation);
	}
	
	public AbstractVector2f center() {
		return new Vector2f().add(min).add(max).mul(1 / 2f);
	}
	
	public float xSize() {
		return max.x - min.x;
	}
	
	public float ySize() {
		return max.y - min.y;
	}
	
}
