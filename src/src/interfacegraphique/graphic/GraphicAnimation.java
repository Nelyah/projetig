package interfacegraphique.graphic;

import interfacegraphique.movie.KeyFrame;
import interfacegraphique.movie.animation.Animation;
import interfacegraphique.movie.animation.EaseInCircularAnimation;
import interfacegraphique.movie.animation.EaseInCubicAnimation;
import interfacegraphique.movie.animation.EaseInExponentialAnimation;
import interfacegraphique.movie.animation.EaseInOutCircularAnimation;
import interfacegraphique.movie.animation.EaseInOutCubicAnimation;
import interfacegraphique.movie.animation.EaseInOutExponentialAnimation;
import interfacegraphique.movie.animation.EaseInOutQuadraticAnimation;
import interfacegraphique.movie.animation.EaseInOutQuarticAnimation;
import interfacegraphique.movie.animation.EaseInOutSinusoidalAnimation;
import interfacegraphique.movie.animation.EaseInQuadraticAnimation;
import interfacegraphique.movie.animation.EaseInQuarticAnimation;
import interfacegraphique.movie.animation.EaseInSinusoidalAnimation;
import interfacegraphique.movie.animation.EaseOutCircularAnimation;
import interfacegraphique.movie.animation.EaseOutCubicAnimation;
import interfacegraphique.movie.animation.EaseOutExponentialAnimation;
import interfacegraphique.movie.animation.EaseOutQuadraticAnimation;
import interfacegraphique.movie.animation.EaseOutQuarticAnimation;
import interfacegraphique.movie.animation.EaseOutSinusoidalAnimation;
import interfacegraphique.movie.animation.LinearAnimation;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class GraphicAnimation extends JPanel {
	private JComboBox<Animation> jcombo;
	private KeyFrame keyframe;
	private boolean updating;

	public GraphicAnimation() {
		this.keyframe = null;
		this.updating = false;
		this.jcombo = new JComboBox<Animation>();

		this.initComponents();
	}

	public void initComponents() {
		this.jcombo.addItem(null);
		this.jcombo.addItem(new LinearAnimation());
		this.jcombo.addItem(new EaseInCircularAnimation());
		this.jcombo.addItem(new EaseInCubicAnimation());
		this.jcombo.addItem(new EaseInExponentialAnimation());
		this.jcombo.addItem(new EaseInOutCircularAnimation());
		this.jcombo.addItem(new EaseInOutCubicAnimation());
		this.jcombo.addItem(new EaseInOutExponentialAnimation());
		this.jcombo.addItem(new EaseInOutQuadraticAnimation());
		this.jcombo.addItem(new EaseInOutQuarticAnimation());
		this.jcombo.addItem(new EaseInOutSinusoidalAnimation());
		this.jcombo.addItem(new EaseInQuadraticAnimation());
		this.jcombo.addItem(new EaseInQuarticAnimation());
		this.jcombo.addItem(new EaseInSinusoidalAnimation());
		this.jcombo.addItem(new EaseOutCircularAnimation());
		this.jcombo.addItem(new EaseOutCubicAnimation());
		this.jcombo.addItem(new EaseOutExponentialAnimation());
		this.jcombo.addItem(new EaseOutQuadraticAnimation());
		this.jcombo.addItem(new EaseOutQuarticAnimation());
		this.jcombo.addItem(new EaseOutSinusoidalAnimation());

		this.add(jcombo);

		this.jcombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!GraphicAnimation.this.updating) {
					KeyFrame kf = GraphicAnimation.this.keyframe;
					if (kf != null) {
						JComboBox<Animation> jcbAnimations = GraphicAnimation.this.jcombo;
						int index = jcbAnimations.getSelectedIndex();
						kf.setAnimation(index > 0 ? jcbAnimations.getItemAt(index) : null);
					}
				}
			}
		});
	}

	public void setKeyFrame(KeyFrame kf) {
		this.updating = true;
		this.keyframe = kf;
		this.jcombo.setEnabled(kf != null);

		if (kf == null) {
			this.jcombo.setSelectedIndex(0);

		} else {

			Animation a = kf.getAnimation();
			if (a == null) {
				this.jcombo.setSelectedIndex(0);
			} else {
				this.jcombo.setSelectedItem(a);
			}
		}

		this.updating = false;
	}
}
