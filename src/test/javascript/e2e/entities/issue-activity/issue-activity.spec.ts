import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IssueActivityComponentsPage, IssueActivityDeleteDialog, IssueActivityUpdatePage } from './issue-activity.page-object';

const expect = chai.expect;

describe('IssueActivity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let issueActivityComponentsPage: IssueActivityComponentsPage;
  let issueActivityUpdatePage: IssueActivityUpdatePage;
  let issueActivityDeleteDialog: IssueActivityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IssueActivities', async () => {
    await navBarPage.goToEntity('issue-activity');
    issueActivityComponentsPage = new IssueActivityComponentsPage();
    await browser.wait(ec.visibilityOf(issueActivityComponentsPage.title), 5000);
    expect(await issueActivityComponentsPage.getTitle()).to.eq('wlbApp.issueActivity.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(issueActivityComponentsPage.entities), ec.visibilityOf(issueActivityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IssueActivity page', async () => {
    await issueActivityComponentsPage.clickOnCreateButton();
    issueActivityUpdatePage = new IssueActivityUpdatePage();
    expect(await issueActivityUpdatePage.getPageTitle()).to.eq('wlbApp.issueActivity.home.createOrEditLabel');
    await issueActivityUpdatePage.cancel();
  });

  it('should create and save IssueActivities', async () => {
    const nbButtonsBeforeCreate = await issueActivityComponentsPage.countDeleteButtons();

    await issueActivityComponentsPage.clickOnCreateButton();

    await promise.all([issueActivityUpdatePage.setActivityInput('activity'), issueActivityUpdatePage.issueSelectLastOption()]);

    expect(await issueActivityUpdatePage.getActivityInput()).to.eq('activity', 'Expected Activity value to be equals to activity');

    await issueActivityUpdatePage.save();
    expect(await issueActivityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await issueActivityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last IssueActivity', async () => {
    const nbButtonsBeforeDelete = await issueActivityComponentsPage.countDeleteButtons();
    await issueActivityComponentsPage.clickOnLastDeleteButton();

    issueActivityDeleteDialog = new IssueActivityDeleteDialog();
    expect(await issueActivityDeleteDialog.getDialogTitle()).to.eq('wlbApp.issueActivity.delete.question');
    await issueActivityDeleteDialog.clickOnConfirmButton();

    expect(await issueActivityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
