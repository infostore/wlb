import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IssueAttachmentComponentsPage, IssueAttachmentDeleteDialog, IssueAttachmentUpdatePage } from './issue-attachment.page-object';

const expect = chai.expect;

describe('IssueAttachment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let issueAttachmentComponentsPage: IssueAttachmentComponentsPage;
  let issueAttachmentUpdatePage: IssueAttachmentUpdatePage;
  let issueAttachmentDeleteDialog: IssueAttachmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IssueAttachments', async () => {
    await navBarPage.goToEntity('issue-attachment');
    issueAttachmentComponentsPage = new IssueAttachmentComponentsPage();
    await browser.wait(ec.visibilityOf(issueAttachmentComponentsPage.title), 5000);
    expect(await issueAttachmentComponentsPage.getTitle()).to.eq('wlbApp.issueAttachment.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(issueAttachmentComponentsPage.entities), ec.visibilityOf(issueAttachmentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IssueAttachment page', async () => {
    await issueAttachmentComponentsPage.clickOnCreateButton();
    issueAttachmentUpdatePage = new IssueAttachmentUpdatePage();
    expect(await issueAttachmentUpdatePage.getPageTitle()).to.eq('wlbApp.issueAttachment.home.createOrEditLabel');
    await issueAttachmentUpdatePage.cancel();
  });

  it('should create and save IssueAttachments', async () => {
    const nbButtonsBeforeCreate = await issueAttachmentComponentsPage.countDeleteButtons();

    await issueAttachmentComponentsPage.clickOnCreateButton();

    await promise.all([issueAttachmentUpdatePage.attachmentSelectLastOption(), issueAttachmentUpdatePage.issueSelectLastOption()]);

    await issueAttachmentUpdatePage.save();
    expect(await issueAttachmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await issueAttachmentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last IssueAttachment', async () => {
    const nbButtonsBeforeDelete = await issueAttachmentComponentsPage.countDeleteButtons();
    await issueAttachmentComponentsPage.clickOnLastDeleteButton();

    issueAttachmentDeleteDialog = new IssueAttachmentDeleteDialog();
    expect(await issueAttachmentDeleteDialog.getDialogTitle()).to.eq('wlbApp.issueAttachment.delete.question');
    await issueAttachmentDeleteDialog.clickOnConfirmButton();

    expect(await issueAttachmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
