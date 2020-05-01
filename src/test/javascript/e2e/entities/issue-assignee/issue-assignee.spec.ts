import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IssueAssigneeComponentsPage, IssueAssigneeDeleteDialog, IssueAssigneeUpdatePage } from './issue-assignee.page-object';

const expect = chai.expect;

describe('IssueAssignee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let issueAssigneeComponentsPage: IssueAssigneeComponentsPage;
  let issueAssigneeUpdatePage: IssueAssigneeUpdatePage;
  let issueAssigneeDeleteDialog: IssueAssigneeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IssueAssignees', async () => {
    await navBarPage.goToEntity('issue-assignee');
    issueAssigneeComponentsPage = new IssueAssigneeComponentsPage();
    await browser.wait(ec.visibilityOf(issueAssigneeComponentsPage.title), 5000);
    expect(await issueAssigneeComponentsPage.getTitle()).to.eq('wlbApp.issueAssignee.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(issueAssigneeComponentsPage.entities), ec.visibilityOf(issueAssigneeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IssueAssignee page', async () => {
    await issueAssigneeComponentsPage.clickOnCreateButton();
    issueAssigneeUpdatePage = new IssueAssigneeUpdatePage();
    expect(await issueAssigneeUpdatePage.getPageTitle()).to.eq('wlbApp.issueAssignee.home.createOrEditLabel');
    await issueAssigneeUpdatePage.cancel();
  });

  it('should create and save IssueAssignees', async () => {
    const nbButtonsBeforeCreate = await issueAssigneeComponentsPage.countDeleteButtons();

    await issueAssigneeComponentsPage.clickOnCreateButton();

    await promise.all([issueAssigneeUpdatePage.userSelectLastOption(), issueAssigneeUpdatePage.issueSelectLastOption()]);

    await issueAssigneeUpdatePage.save();
    expect(await issueAssigneeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await issueAssigneeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last IssueAssignee', async () => {
    const nbButtonsBeforeDelete = await issueAssigneeComponentsPage.countDeleteButtons();
    await issueAssigneeComponentsPage.clickOnLastDeleteButton();

    issueAssigneeDeleteDialog = new IssueAssigneeDeleteDialog();
    expect(await issueAssigneeDeleteDialog.getDialogTitle()).to.eq('wlbApp.issueAssignee.delete.question');
    await issueAssigneeDeleteDialog.clickOnConfirmButton();

    expect(await issueAssigneeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
