package engine.backend;

import engine.frontend.game_engine_UI.MenuView.DialogueMenu;

public class DialogueInteraction extends Interaction {
	DialogueTreeNode myRoot;
	DialogueTreeNode myCurrentNode;
	DialogueMenu myMenu;

	public DialogueInteraction(DialogueTreeNode root) {
		myRoot = root;
		myCurrentNode = root;
		setMenu();
	}

	public DialogueTreeNode getRoot() {
		return myRoot;
	}

	public DialogueTreeNode getCurrentNode() {
		return myCurrentNode;
	}

	public void getNextNode(String response) {
		myCurrentNode = myCurrentNode.getChild(response);
		setMenu();
	}

	public void setMenu() {
		myMenu = new DialogueMenu(ServiceLocator.getController(), myCurrentNode.getDialogue());
		ServiceLocator.getController().addDialogue(myCurrentNode.getDialogue());
		myMenu.addListView(myCurrentNode.getResponsesCommands());
	}
}
