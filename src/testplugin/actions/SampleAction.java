package testplugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.StorageException;

/**
 * Our sample action implements workbench action delegate. The action proxy will be created by the workbench and shown in the UI. When the
 * user tries to use the action, this delegate will be created and execution will be delegated to it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow window;

    /**
     * The constructor.
     */
    public SampleAction() {
    }

    /**
     * The action has been activated. The argument of the method represents the 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        try {
            ISecurePreferences root = SecurePreferencesFactory.getDefault();

            String nodes = printnode(0,root);
            MessageDialog.openInformation(window.getShell(), "SecureStorage", nodes);
        } catch (StorageException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
    
    public String printnode(int i, ISecurePreferences node) throws StorageException{
        StringBuffer ibuffer = new StringBuffer();
        for (int n = 0; n < i; n++) {
            ibuffer.append("  ");
        }
        String indent = ibuffer.toString();
        StringBuffer buffer = new StringBuffer();
        buffer.append(printkeys(i, node));
        String children[] = node.childrenNames();
        for(int n = 0; n < children.length; n++){
            buffer.append(indent).append("node: ").append(children[n]).append(":\n");
            buffer.append(printnode(i+1, node.node(children[n])));
        }
        System.out.println(buffer.toString());
        return buffer.toString();
        
    }

    public String printkeys(int i, ISecurePreferences node) throws StorageException {
        StringBuffer buffer = new StringBuffer();
        for (int n = 0; n < i; n++) {
            buffer.append("  ");
        }
        String indent = buffer.toString();
        buffer = new StringBuffer();

        String keys[] = node.keys();

        for (int n = 0; n < keys.length; n++) {
            buffer.append(indent).append(keys[n]).append(":").append(node.get(keys[n], "N/A")).append("\n");
        }
        return buffer.toString();
    }

    /**
     * Selection in the workbench has been changed. We can change the state of the 'real' action here if we want, but this can only happen
     * after the delegate has been created.
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }

    /**
     * We can use this method to dispose of any system resources we previously allocated.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() {
    }

    /**
     * We will cache window object in order to be able to provide parent shell for the message dialog.
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init(IWorkbenchWindow window) {
        this.window = window;
    }
}