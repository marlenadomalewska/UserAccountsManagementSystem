package com.accountmanagement.views;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.accountmanagement.data.entity.Account;
import com.accountmanagement.data.entity.Gender;
import com.accountmanagement.data.service.AccountService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("User Account Details")
@Route("details/:id_acc?")
@AnonymousAllowed
public class AccountDetailsView extends VerticalLayout implements BeforeEnterObserver {

	private final AccountService service;

	private TextField tfUsername;
	private PasswordField pfPassword;
	private IntegerField ifAge;
	private RadioButtonGroup<Gender> rGender;
	private Button bSave;
	private Button bExit;
	private Account account;

	public AccountDetailsView(AccountService service) {
		super();
		this.service = service;
		createGUI();
		addListeners();
	}

	private void createGUI() {
		tfUsername = new TextField("Username");
		tfUsername.setRequired(true);
		pfPassword = new PasswordField("Password");
		pfPassword.setRequired(true);
		pfPassword.setRevealButtonVisible(false);
		ifAge = new IntegerField("Age");
		ifAge.setRequired(true);
		rGender = new RadioButtonGroup<>("Gender");
		rGender.setItems(Gender.values());
		rGender.setValue(Gender.MALE);
		rGender.setRenderer(new TextRenderer<>(gender -> gender.getDescription()));
		bSave = new Button("Save");
		bExit = new Button("Exit");

		add(tfUsername, pfPassword, ifAge, rGender,
			new HorizontalLayout(bSave, bExit));

		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	}

	private void addListeners() {
		bSave.addClickListener(event -> doOnSave());
		bExit.addClickListener(event -> {
			getUI().ifPresent(ui -> ui.navigate(AccountGridView.class));
		});
	}

	private void doOnSave() {
		if (tfUsername.isInvalid() || ifAge.isInvalid() || pfPassword.isInvalid()) {
			return;
		}
		if (account == null) {
			if (service.isUsernameAvailable(tfUsername.getValue())) {
				service.addAccount(buildAccount());
			} else {
				Notification.show("Username is already in use");
				return;
			}
		} else {
			service.editAccount(buildAccount());
		}
		Notification.show("Account saved");
		getUI().ifPresent(ui -> ui.navigate(AccountGridView.class));
	}

	private Account buildAccount() {
		if (account == null) {
			account = new Account();
		}
		account.setAge(ifAge.getValue());
		account.setGender(rGender.getValue());
		account.setUsername(tfUsername.getValue());
		account.setPassword(new BCryptPasswordEncoder(16).encode(pfPassword.getValue()));
		return account;
	}

	private void loadAccount(Account account) {
		this.account = account;
		if (account == null) {
			return;
		}
		tfUsername.setValue(account.getUsername());
		ifAge.setValue(account.getAge());
		rGender.setValue(account.getGender());
		pfPassword.setValue(account.getPassword());
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Optional<String> idParameter = event.getRouteParameters().get("id_acc");
		if (idParameter.isPresent()) {
			tfUsername.setReadOnly(true);
			pfPassword.setReadOnly(true);
			loadAccount(service.getAccountById(Integer.parseInt(idParameter.get())));
		}
	}
}
