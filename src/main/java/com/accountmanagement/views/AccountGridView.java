package com.accountmanagement.views;

import java.time.format.DateTimeFormatter;

import com.accountmanagement.data.entity.AccountDTO;
import com.accountmanagement.data.service.AccountService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("User Accounts Management System")
@Route("")
@AnonymousAllowed
public class AccountGridView extends VerticalLayout {

	private final AccountService service;
	private Grid<AccountDTO> grid;
	private Button bAdd;

	public AccountGridView(AccountService service) {
		super();
		this.service = service;

		bAdd = new Button("Add account", event -> {
			bAdd.getUI().ifPresent(ui -> ui.navigate(AccountDetailsView.class));
		});

		grid = new Grid<>(AccountDTO.class, false);
		grid.addColumn(AccountDTO::getUsername).setHeader("Username").setWidth("500px");
		grid.addColumn(source -> source.getGender().getDescription()).setHeader("Gender").setWidth("200px");
		grid.addColumn(AccountDTO::getAge).setHeader("Age").setWidth("100px");
		grid.addColumn(source -> source.getCreationTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).setHeader("Created")
			.setWidth("100px");
		grid.addComponentColumn(source -> new HorizontalLayout(
			initButtonEdit(source), initButtonDelete(source))).setAutoWidth(true);

		add(bAdd, grid);
		loadAll();
	}

	private Button initButtonEdit(AccountDTO source) {
		return new Button("Edit", event -> {
			getUI().ifPresent(ui -> ui.navigate(AccountDetailsView.class, new RouteParameters("id_acc", String.valueOf(source.getIdAcc()))));
		});
	}

	private Button initButtonDelete(AccountDTO source) {
		return new Button("Delete", event -> {
			service.deleteAccount(source.getIdAcc());
			Notification.show("Account deleted");
			loadAll();
		});
	}

	private void loadAll() {
		grid.setItems(service.getAllAccounts());
	}
}
