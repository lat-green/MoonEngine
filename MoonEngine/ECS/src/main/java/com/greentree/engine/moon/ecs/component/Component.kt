package com.greentree.engine.moon.ecs.component

import com.greentree.engine.moon.ecs.Copiable
import java.io.Serializable

interface Component : Serializable, Copiable<Component>