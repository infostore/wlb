import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IssueComponentsPage, IssueDeleteDialog, IssueUpdatePage } from './issue.page-object';

const expect = chai.expect;

describe('Issue e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let issueComponentsPage: IssueComponentsPage;
  let issueUpdatePage: IssueUpdatePage;
  let issueDeleteDialog: IssueDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Issues', async () => {
    await navBarPage.goToEntity('issue');
    issueComponentsPage = new IssueComponentsPage();
    await browser.wait(ec.visibilityOf(issueComponentsPage.title), 5000);
    expect(await issueComponentsPage.getTitle()).to.eq('wlbApp.issue.home.title');
    await browser.wait(ec.or(ec.visibilityOf(issueComponentsPage.entities), ec.visibilityOf(issueComponentsPage.noResult)), 1000);
  });

  it('should load create Issue page', async () => {
    await issueComponentsPage.clickOnCreateButton();
    issueUpdatePage = new IssueUpdatePage();
    expect(await issueUpdatePage.getPageTitle()).to.eq('wlbApp.issue.home.createOrEditLabel');
    await issueUpdatePage.cancel();
  });

  it('should create and save Issues', async () => {
    const nbButtonsBeforeCreate = await issueComponentsPage.countDeleteButtons();

    await issueComponentsPage.clickOnCreateButton();

    await promise.all([
      issueUpdatePage.setNameInput('name'),
      issueUpdatePage.setDescriptionInput('description'),
      issueUpdatePage.issueTypeSelectLastOption(),
      issueUpdatePage.issueStatusSelectLastOption(),
      issueUpdatePage.prioritySelectLastOption(),
      issueUpdatePage.resolutionSelectLastOption(),
      issueUpdatePage.setDueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      issueUpdatePage.projectSelectLastOption(),
      issueUpdatePage.milestoneSelectLastOption()
    ]);

    expect(await issueUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await issueUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await issueUpdatePage.getDueDateInput()).to.contain('2001-01-01T02:30', 'Expected dueDate value to be equals to 2000-12-31');

    await issueUpdatePage.save();
    expect(await issueUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await issueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Issue', async () => {
    const nbButtonsBeforeDelete = await issueComponentsPage.countDeleteButtons();
    await issueComponentsPage.clickOnLastDeleteButton();

    issueDeleteDialog = new IssueDeleteDialog();
    expect(await issueDeleteDialog.getDialogTitle()).to.eq('wlbApp.issue.delete.question');
    await issueDeleteDialog.clickOnConfirmButton();

    expect(await issueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
