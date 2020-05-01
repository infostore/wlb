import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IssueLabelComponentsPage, IssueLabelDeleteDialog, IssueLabelUpdatePage } from './issue-label.page-object';

const expect = chai.expect;

describe('IssueLabel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let issueLabelComponentsPage: IssueLabelComponentsPage;
  let issueLabelUpdatePage: IssueLabelUpdatePage;
  let issueLabelDeleteDialog: IssueLabelDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IssueLabels', async () => {
    await navBarPage.goToEntity('issue-label');
    issueLabelComponentsPage = new IssueLabelComponentsPage();
    await browser.wait(ec.visibilityOf(issueLabelComponentsPage.title), 5000);
    expect(await issueLabelComponentsPage.getTitle()).to.eq('wlbApp.issueLabel.home.title');
    await browser.wait(ec.or(ec.visibilityOf(issueLabelComponentsPage.entities), ec.visibilityOf(issueLabelComponentsPage.noResult)), 1000);
  });

  it('should load create IssueLabel page', async () => {
    await issueLabelComponentsPage.clickOnCreateButton();
    issueLabelUpdatePage = new IssueLabelUpdatePage();
    expect(await issueLabelUpdatePage.getPageTitle()).to.eq('wlbApp.issueLabel.home.createOrEditLabel');
    await issueLabelUpdatePage.cancel();
  });

  it('should create and save IssueLabels', async () => {
    const nbButtonsBeforeCreate = await issueLabelComponentsPage.countDeleteButtons();

    await issueLabelComponentsPage.clickOnCreateButton();

    await promise.all([issueLabelUpdatePage.issueSelectLastOption(), issueLabelUpdatePage.labelSelectLastOption()]);

    await issueLabelUpdatePage.save();
    expect(await issueLabelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await issueLabelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last IssueLabel', async () => {
    const nbButtonsBeforeDelete = await issueLabelComponentsPage.countDeleteButtons();
    await issueLabelComponentsPage.clickOnLastDeleteButton();

    issueLabelDeleteDialog = new IssueLabelDeleteDialog();
    expect(await issueLabelDeleteDialog.getDialogTitle()).to.eq('wlbApp.issueLabel.delete.question');
    await issueLabelDeleteDialog.clickOnConfirmButton();

    expect(await issueLabelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
