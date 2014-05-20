package interfacegraphique.tools;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentAdapter implements DocumentListener {
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.performUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.performUpdate(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		this.performUpdate(e);
	}

	public abstract void performUpdate(DocumentEvent e);
}
