/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.lpui;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 *
 * @author Leonard
 */
public class LPComponent {

    /**
     * The component that currently has focus.
     */
    private static LPComponent focused;
    /**
     * Static Reference to the PApplet.
     */
    private static LPBase staticApplet;
    /**
     * The base applet.
     */
    private PApplet baseApplet;
    /**
     * horizontal position in the applet.
     */
    private float posX;
    /**
     * Vertical position in the applet.
     */
    private float posY;
    /**
     * Width of this component.
     */
    private float width;
    /**
     * Height of this component.
     */
    private float height;
    /**
     * Flags that this component needs to be redrawn.
     */
    private boolean isInvalid = false;
    /**
     * Name for this component.
     */
    private String name = "";
    /**
     * The parent component for this component.
     */
    private LPComponent parent;
    /**
     * Indicates that the mouse in ontop of this component.
     */
    private boolean isMouseOntop = false;
    /**
     * Horizontal mouse position on this component.
     */
    private int mouseX;
    /**
     * Vertical mouse position on this component.
     */
    private int mouseY;
    /**
     * True if input to this component is enabled.
     */
    private boolean isEnabled = true;
    /**
     * True if the component is currently visible and should be drawn.
     */
    private boolean visible = true;
    /**
     * Indicates that an event should be propagated to this components parent.
     */
    private boolean propagateEventToParent = true;
    /**
     * List of animation tasks for this component.
     */
    private final List<LPAnimationTask> animationTasks = new ArrayList<>();

    /**
     * Creates a new instance.
     */
    public LPComponent() {
    }

    /**
     * Sets the position of this component.
     *
     * @param x X-position.
     * @param y Y-position.
     */
    public void setPosition(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Returns the X-position.
     *
     * @return the X-position.
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Returns the Y-position.
     *
     * @return the Y-position.
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Sets the size for this component.
     *
     * @param w Width.
     * @param h Height.
     */
    public void setSize(float w, float h) {
        this.width = w;
        this.height = h;
        onResize(w, h);
        invalidate();
    }

    /**
     * Returns the current width of this component.
     *
     * @return the width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the current height of this component.
     *
     * @return the height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Set the base PApplet.
     *
     * @param a PApplet.
     */
    public static void setStaticApplet(LPBase a) {
        staticApplet = a;
    }

    /**
     * Returns the static applet.
     *
     * @return PApplet
     */
    public static LPBase getStaticApplet() {
        return staticApplet;
    }

    /**
     * Sets the base applet for this component.
     *
     * @param applet PApplet.
     */
    public void setBaseApplet(PApplet applet) {
        baseApplet = applet;
    }

    /**
     * Returns the applet for this component. If the applet cannot be found it
     * defaults to the base applet.
     *
     * @return the applet.
     */
    public PApplet getApplet() {
        if (parent == null) {
            if (baseApplet == null) {
                return staticApplet;
            } else {
                return baseApplet;
            }
        }
        return parent.getApplet();
    }

    /**
     * Set the parent element for this component.
     *
     * @param parent parent component.
     */
    protected void setParent(LPComponent parent) {
        this.parent = parent;
    }

    /**
     * Set name for this component.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this component.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Invalidate this component to mark it to be redrawn.
     */
    public void invalidate() {
        isInvalid = true;
    }

    /**
     * Invalidates the parent of this component.
     */
    public void invalidateParent() {
        if (parent != null) {
            parent.invalidate();
        }
    }

    /**
     * Returns if the component is invalid.
     *
     * @return
     */
    public boolean isInvalid() {
        return isInvalid;
    }

    /**
     * Returns true if this component is the one currently focused.
     *
     * @return
     */
    protected boolean isFocused() {
        return this == focused;
    }

    /**
     * Sets the focused component.
     *
     * @param comp the focused component.
     */
    protected static void setFocused(LPComponent comp) {
        requireNonNull(comp, "comp");
        if (comp == focused) {
            return;
        }
        if (focused != null) {
            focused.onFocusLost();
        }
        focused = comp;
        focused.onFocusGained();
    }

    /**
     * Returns the currently focused component.
     *
     * @return the focused component.
     */
    protected static LPComponent getFocused() {
        return focused;
    }

    /**
     * Event for when a component gains focus. Override this event to be able to
     * react to it.
     */
    public void onFocusGained() {
    }

    /**
     * Event for when a component loses focus. Override this event to be able to
     * react to it.
     */
    public void onFocusLost() {
    }

    public boolean isEnabled() {
        return isEnabled && (parent == null ? parent.isEnabled() : true);
    }

    public void setEnabled(boolean isEnabled) {
        if (this.isEnabled != isEnabled) {
            this.isEnabled = isEnabled;
            if (isEnabled()) {
                onEnabled();
            } else {
                onDisabled();
            }
            invalidate();
        }
    }

    /**
     * Event for when a componentis enabled or disabled. Override this event to
     * be able to react to it.
     */
    public void onEnabled() {
    }

    /**
     * Event for when a componentis enabled or disabled. Override this event to
     * be able to react to it.
     */
    public void onDisabled() {
    }

    /**
     * Sets the visibility status for this component.
     *
     * @param visible True if the component should be drawn.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        invalidate();
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Draws this component.Used internaly.
     *
     * @param applet the target applet.
     */
    protected void drawInternal(PApplet applet) {
        if (isInvalid && visible) {
            draw(applet);
            isInvalid = false;
        }
    }

    /**
     * Override this method to draw this component.
     *
     * @param applet the target applet.
     */
    public void draw(PApplet applet) {
    }

    /**
     * Mouse pressed event. Used internaly, do not call directly.
     *
     * @param mouseX X-position of the mouse click.
     * @param mouseY Y-position of the mouse click.
     * @param mouseButton which mouse button was pressed.
     * @return Returns the top most component that was pressed.
     */
    protected LPComponent onMousePressedInternal(int mouseX, int mouseY, int mouseButton) {
        if (!isVisible()) {
            return null;
        }
        LPComponent clickedComponent = null;
        if (mouseX >= posX && mouseX < posX + width
                && mouseY >= posY && mouseY < posY + height) {
            clickedComponent = this;

            //run mouse pressed event for this component.
            onMousePressed((int) (mouseX - posX), (int) (mouseY - posY), mouseButton);
        }
        return clickedComponent;
    }

    /**
     * Mouse button pressed event. Override this event to be able to react to
     * it.
     *
     * @param x the x position of the cursor on this component.
     * @param y the y position of the corsor on this component.
     * @param button the button type for the press. either LEFT, RIGHT or CENTER
     */
    public void onMousePressed(int x, int y, int button) {
    }

    /**
     * Mouse released event. Used internaly, do not call directly.
     *
     * @param mouseX X-position of the mouse click.
     * @param mouseY Y-position of the mouse click.
     * @param mouseButton which mouse button was pressed.
     */
    public void onMouseReleasedInternal(int mouseX, int mouseY, int mouseButton) {
        if (!isVisible()) {
            return;
        }
        onMouseReleased(mouseX, mouseY, mouseButton);
        if (parent != null
                && propagateEventToParent) {
            parent.onMouseReleasedInternal(mouseX, mouseY, mouseButton);
        }
        propagateEventToParent = true;
    }

    /**
     * Mouse button released event. Override this event to be able to react to
     * it.
     *
     * @param x the x position of the cursor on this component.
     * @param y the y position of the corsor on this component.
     * @param button the button type for the press. either LEFT, RIGHT or CENTER
     */
    public void onMouseReleased(int x, int y, int button) {
    }

    /**
     * Mouse scroll event.Used internaly, do not call directly.
     *
     * @param mouseX X-position of the mouse click.
     * @param mouseY Y-position of the mouse click.
     * @param scrolDir direction of the scroll.
     * @return The component the mouse was over when the scroll happened.
     */
    public LPComponent onMouseScrollInternal(int mouseX, int mouseY, int scrolDir) {
        if (!isVisible()) {
            return null;
        }
        LPComponent target = null;
        if (mouseX > posX && mouseX < posX + width
                && mouseY > posY && mouseY < posY + height) {
            target = this;
            onMouseScroll(scrolDir);

        }
        return target;
    }

    /**
     * Mouse scroll event. Override this event to be able to react to it.
     *
     * @param scrollDir Direction of the scroll.
     */
    public void onMouseScroll(int scrollDir) {
    }

    /**
     * Resize event.
     *
     * @param w new width of this component.
     * @param h new height of this component.
     */
    public void onResize(float w, float h) {

    }

    /**
     * Event for when the mouse enters the bounding box of this component.
     * Override this event to be able to react to it.
     */
    public void onMouseEnter() {
    }

    /**
     * Event for when the mouse leaves the bounding box of this component.
     * Override this event to be able to react to it.
     */
    public void onMouseLeave() {
    }

    /**
     * Event for when the mouse moves across this compoennt. Override this event
     * to be able to react to it.
     *
     * @param x the current x position for the mouse.
     * @param y the current y position for the mouse.
     */
    public void onMouseMove(int x, int y) {
    }

    /**
     * Returns the current mouse x position.
     *
     * @return the current mouse x position.
     */
    public int mouseX() {
        return mouseX;
    }

    /**
     * Returns the current mouse y position.
     *
     * @return the mouse y position.
     */
    public int mouseY() {
        return mouseY;
    }

    /**
     * Returns true when the mouse if currently over this component.
     *
     * @return
     */
    public boolean isMouseOver() {
        return isMouseOntop;
    }

    /**
     * Mouse enter event. Used internally, do not call directly.
     */
    public void onMouseEnterInternal() {
        if (!isVisible()) {
            return;
        }
        isMouseOntop = true;
        onMouseEnter();
    }

    /**
     * Mouse leave event. Used internally, do not call directly.
     */
    public void onMouseLeaveInternal() {
        if (!isVisible()) {
            return;
        }
        isMouseOntop = false;
        onMouseLeave();
    }

    /**
     * Mouse move event. Used internally, do not call directly.
     *
     * @param x the current x position for the mouse.
     * @param y the current y position for the mouse.
     */
    public void onMouseMoveInternal(int x, int y) {
        if (!isVisible()) {
            return;
        }
        if (x > posX && x < posX + width
                && y > posY && y < posY + height) {
            if (!isMouseOntop) {
                onMouseEnterInternal();
            }
            mouseX = x;
            mouseY = y;
            onMouseMove((int) (x - posX), (int) (y - posY));
        } else {
            if (isMouseOntop) {
                onMouseLeaveInternal();
            }
        }
    }

    /**
     * Key pressed event. Used internally, do not call directly.
     *
     * @param event The key event
     */
    public void onKeyPressedInternal(KeyEvent event) {
        if (!isVisible()) {
            return;
        }
        onKeyPressed(event);
        if (parent == null) {
            getStaticApplet().keyPressedFallthrough(event);
        } else if (propagateEventToParent) {
            parent.onKeyPressedInternal(event);
        }
        propagateEventToParent = true;
    }

    /**
     * Key pressed event. Override this event to be able to react to it.
     *
     * @param event the key event.
     */
    public void onKeyPressed(KeyEvent event) {
    }

    /**
     * Key released event. Used internally, do not call directly.
     *
     * @param event the key event.
     */
    public void onKeyReleasedInternal(KeyEvent event) {
        if (!isVisible()) {
            return;
        }
        onKeyReleased(event);

        if (parent == null) {
            getStaticApplet().keyReleasedFallthrough(event);
        } else if (propagateEventToParent) {
            parent.onKeyReleasedInternal(event);
        }
        propagateEventToParent = true;
    }

    /**
     * Key released event. Override this event to be able to react to it.
     *
     * @param event the key event.
     */
    public void onKeyReleased(KeyEvent event) {
    }

    /**
     * Stops the propagation of events to the parent.
     */
    public void stopPropagation() {
        propagateEventToParent = false;
    }

    /**
     * Adds an animation task to this component.
     *
     * @param task the task to add.
     */
    protected void addAnimationTask(LPAnimationTask task) {
        animationTasks.add(task);
    }

    /**
     * Removes an animation task from this component.
     *
     * @param task the task to remove.
     */
    protected void removeAnimationTask(LPAnimationTask task) {
        animationTasks.remove(task);
    }

    /**
     * Gets called to animate this component. Used internally.
     *
     * @param dt delta time since the last frame in milliseconds.
     */
    void animateInternal(int dt) {
        animationTasks.forEach(task -> task.animate(dt));
    }

}
