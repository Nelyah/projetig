package interfacegraphique.svg;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.DrawableGroup;
import interfacegraphique.drawing.shape.Circle;
import interfacegraphique.drawing.shape.Oval;
import interfacegraphique.drawing.shape.Polygon;
import interfacegraphique.drawing.shape.Rectangle;
import interfacegraphique.drawing.shape.Shape;
import interfacegraphique.movie.KeyFrame;
import interfacegraphique.movie.Layer;
import interfacegraphique.movie.Project;
import interfacegraphique.movie.Scenario;
import interfacegraphique.movie.animation.Animation;
import interfacegraphique.tools.Tools;

import java.awt.Color;
import java.awt.Point;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Svg {
	private Document document;
	private Element root;
	private Project project;

	public Svg(Project p) throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();

		this.project = p;
		this.document = builder.newDocument();

		this.initComponents();
	}

	private void initComponents() {
		this.root = this.document.createElement("svg");
		this.root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");
		this.root.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		this.document.appendChild(this.root);

		this.setAttribute("style", "background-color:" + this.colorRGBA(this.project.getBackgroundColor()) + ";");
		int fps = this.project.getScenario().getFps();

		Scenario sc = this.project.getScenario();
		ArrayList<Layer> layers = sc.getLayers();
		for (int i = layers.size() - 1; i >= 0; i--) {
			Layer layer = layers.get(i);

			ArrayList<Drawable> drawables = layer.getDrawables();

			for (Drawable d : drawables) {
				Element element = this.createElement(d);
				element.appendChild(this.createAnimateColor("fill", 0, 0, "rgba(0,0,0,0)", "rgba(0,0,0,0)"));
				element.appendChild(this.createAnimateColor("stroke", 0, 0, "rgba(0,0,0,0)", "rgba(0,0,0,0)"));

				int previousTime = -1;
				Drawable previousState = null;

				for (int j = 0, layerSize = layer.size(); j < layerSize; j++) {
					KeyFrame kf = layer.get(j);
					if (kf != null) {
						if (kf.contains(d)) {
							Animation animation = kf.getAnimation();
							Drawable currentState = kf.get(d);
							if (previousState == null) {
								previousState = currentState;
							}

							double begin = (double) (previousTime >= 0 && animation != null ? previousTime : j) / fps;
							double end = (double) j / fps;

							element.appendChild(this.document.createComment("KeyFrame " + j));
							for (Element animate : this.createAnimates(begin, end, d, previousState, currentState)) {
								element.appendChild(animate);
							}

							previousTime = j;
							previousState = currentState;

						} else {
							element.appendChild(this.document.createComment("KeyFrame " + j));
							element.appendChild(this.createAnimateColor("fill", j, j, "rgba(0,0,0,0)", "rgba(0,0,0,0)"));
							element.appendChild(this.createAnimateColor("stroke", j, j, "rgba(0,0,0,0)", "rgba(0,0,0,0)"));
						}
					}
				}

				this.root.appendChild(this.document.createComment(d.getClass().getSimpleName() + " - " + d.getName()));
				this.root.appendChild(element);
			}
		}
	}

	public ArrayList<Element> createAnimates(double begin, double end, Drawable origin, Drawable from, Drawable to) {
		ArrayList<Element> animations = new ArrayList<Element>();

		String translateFrom = (from.getOrigin().x - origin.getOrigin().x) + " " + (from.getOrigin().y - origin.getOrigin().y);
		String translateTo = (to.getOrigin().x - origin.getOrigin().x) + " " + (to.getOrigin().y - origin.getOrigin().y);
		animations.add(this.createAnimateTransform("translate", begin, end, translateFrom, translateTo, false));

		double scaleXFrom = Tools.round(from.getWidth() / (double) origin.getWidth(), 2);
		double scaleYFrom = Tools.round(from.getHeight() / (double) origin.getHeight(), 2);
		double scaleXTo = Tools.round(to.getWidth() / (double) origin.getWidth(), 2);
		double scaleYTo = Tools.round(to.getHeight() / (double) origin.getHeight(), 2);

		String rotateFrom = from.getAngle() + " " + origin.getCenter().x * scaleXFrom + " " + origin.getCenter().y * scaleYFrom;
		String rotateTo = to.getAngle() + " " + origin.getCenter().x * scaleXTo + " " + origin.getCenter().y * scaleYTo;
		animations.add(this.createAnimateTransform("rotate", begin, end, rotateFrom, rotateTo, true));

		if (from instanceof Polygon) {
			animations.add(this.createAnimateTransform("scale", begin, end, scaleXFrom + " " + scaleYFrom, scaleXTo + " " + scaleYTo, true));
		}

		if (from instanceof Shape) {
			String cFrom = this.colorRGBA(((Shape) from).getColor());
			String cTo = this.colorRGBA(((Shape) to).getColor());
			animations.add(this.createAnimateColor("stroke", begin, end, cFrom, cTo));
			animations.add(this.createAnimate("stroke-width", begin, end, ((Shape) from).getStrokeWidth() + "", ((Shape) to).getStrokeWidth() + ""));
			animations.add(this.createAnimate("stroke-opacity", begin, end, from.getOpacity() + "", to.getOpacity() + ""));

			if (from instanceof Polygon) {
				cFrom = this.colorRGBA(((Polygon) from).getBackgroundColor());
				cTo = this.colorRGBA(((Polygon) to).getBackgroundColor());
				animations.add(this.createAnimateColor("fill", begin, end, cFrom, cTo));
				animations.add(this.createAnimate("fill-opacity", begin, end, from.getOpacity() + "", to.getOpacity() + ""));
			}
		}

		return animations;
	}

	public Element createAnimate(String attributeName, double begin, double end, String from, String to) {
		Element animate = this.createElement("animate");
		animate.setAttribute("attributeType", "XML");
		this.setAnimationAttributes(animate, attributeName, begin, end, from, to);

		return animate;
	}

	public Element createAnimateColor(String attributeName, double begin, double end, String from, String to) {
		Element animate = this.createElement("animateColor");
		animate.setAttribute("attributeType", "CSS");
		this.setAnimationAttributes(animate, attributeName, begin, end, from, to);

		return animate;
	}

	public Element createAnimateTransform(String type, double begin, double end, String from, String to, boolean add) {
		Element animate = this.createElement("animateTransform");
		animate.setAttribute("attributeType", "XML");
		animate.setAttribute("type", type);
		if (add) {
			animate.setAttribute("additive", "sum");
		}
		this.setAnimationAttributes(animate, "transform", begin, end, from, to);

		return animate;
	}

	private void setAnimationAttributes(Element animate, String attributeName, double begin, double end, String from, String to) {
		animate.setAttribute("attributeName", attributeName);
		animate.setAttribute("begin", begin + "s");
		animate.setAttribute("dur", (end - begin) + "s");
		animate.setAttribute("from", from);
		animate.setAttribute("to", to);
		animate.setAttribute("fill", "freeze");
	}

	public Element createSymbol(Drawable d) {
		Element symbol = this.document.createElement("symbol");
		symbol.appendChild(this.createElement(d));

		return symbol;
	}

	public Element createElement(Drawable drawable) {
		Element element = null;

		if (drawable instanceof DrawableGroup) {
			element = this.document.createElement("g");
			for (Drawable d : ((DrawableGroup) drawable).getDrawables()) {
				element.appendChild(this.createElement(d));
			}

		} else if (drawable instanceof Circle) {
			element = this.createElement("circle");
			element.setAttribute("cx", drawable.getCenter().x + "");
			element.setAttribute("cy", drawable.getCenter().y + "");
			element.setAttribute("r", drawable.getWidth() / 2 + "");

		} else if (drawable instanceof Oval) {
			element = this.createElement("ellipse");
			element.setAttribute("cx", drawable.getCenter().x + "");
			element.setAttribute("cy", drawable.getCenter().y + "");
			element.setAttribute("rx", drawable.getWidth() / 2 + "");
			element.setAttribute("ry", drawable.getHeight() / 2 + "");

		} else if (drawable instanceof Rectangle) {
			element = this.createElement("rect");
			element.setAttribute("x", drawable.getOrigin().x + "");
			element.setAttribute("y", drawable.getOrigin().y + "");
			element.setAttribute("width", drawable.getWidth() + "");
			element.setAttribute("height", drawable.getHeight() + "");

		} else if (drawable instanceof Shape) {
			String points = "";
			for (Point p : ((Shape) drawable).getPoints()) {
				points += (p.x + "," + p.y + " ");
			}

			if (drawable instanceof Polygon) {
				element = this.createElement("polygon");
			} else {
				element = this.createElement("polyline");
			}

			element.setAttribute("points", points);
		}

		if (drawable instanceof Shape) {
			element.setAttribute("stroke-width", ((Shape) drawable).getStrokeWidth() + "");
			element.setAttribute("stroke", colorRGBA(((Shape) drawable).getColor()));

			if (drawable instanceof Polygon) {
				element.setAttribute("fill", colorRGBA(((Polygon) drawable).getBackgroundColor()));
			}
		}

		element.setAttribute("transform", "rotate(" + drawable.getAngle() + "," + drawable.getCenter().x + "," + drawable.getCenter().y + ")");
		element.setAttribute("fill-opacity", drawable.getOpacity() + "");
		element.setAttribute("stroke-opacity", drawable.getOpacity() + "");

		return element;
	}

	public String colorRGBA(Color c) {
		return "rgba(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + "," + Tools.round(c.getAlpha() / 255.0, 2) + ")";
	}

	public Element createElement(String tagName) {
		return this.document.createElement(tagName);
	}

	public void append(Drawable drawable) {
		this.root.appendChild(this.createElement(drawable));
	}

	public void append(Drawable drawable, Element parent) {
		parent.appendChild(this.createElement(drawable));
	}

	public void setAttribute(String name, String value) {
		this.root.setAttribute(name, value);
	}

	public Document getDocument() {
		return this.document;
	}

	public String toXMLString() throws TransformerException {
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(this.document);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		transformer.transform(source, result);

		return result.getWriter().toString();
	}
}
