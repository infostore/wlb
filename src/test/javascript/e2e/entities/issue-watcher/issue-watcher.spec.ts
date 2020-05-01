import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IssueWatcherComponentsPage, IssueWatcherDeleteDialog, IssueWatcherUpdatePage } from './issue-watcher.page-object';

const expect = chai.expect;

describe('IssueWatcher e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let issueWatcherComponentsPage: IssueWatcherComponentsPage;
  let issueWatcherUpdatePage: IssueWatcherUpdatePage;
  let issueWatcherDeleteDialog: IssueWatcherDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IssueWatchers', async () => {
    await navBarPage.goToEntity('issue-watcher');
    issueWatcherComponentsPage = new IssueWatcherComponentsPage();
    await browser.wait(ec.visibilityOf(issueWatcherComponentsPage.title), 5000);
    expect(await issueWatcherComponentsPage.getTitle()).to.eq('wlbApp.issueWatcher.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(issueWatcherComponentsPage.entities), ec.visibilityOf(issueWatcherComponentsPage.noResult)),
      1000
    );
  });

  it('should load create IssueWatcher page', async () => {
    await issueWatcherComponentsPage.clickOnCreateButton();
    issueWatcherUpdatePage = new IssueWatcherUpdatePage();
    expect(await issueWatcherUpdatePage.getPageTitle()).to.eq('wlbApp.issueWatcher.home.createOrEditLabel');
    await issueWatcherUpdatePage.cancel();
  });

  it('should create and save IssueWatchers', async () => {
    const nbButtonsBeforeCreate = await issueWatcherComponentsPage.countDeleteButtons();

    await issueWatcherComponentsPage.clickOnCreateButton();

    await promise.all([issueWatcherUpdatePage.userSelectLastOption(), issueWatcherUpdatePage.issueSelectLastOption()]);

    await issueWatcherUpdatePage.save();
    expect(await issueWatcherUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await issueWatcherComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last IssueWatcher', async () => {
    const nbButtonsBeforeDelete = await issueWatcherComponentsPage.countDeleteButtons();
    await issueWatcherComponentsPage.clickOnLastDeleteButton();

    issueWatcherDeleteDialog = new IssueWatcherDeleteDialog();
    expect(await issueWatcherDeleteDialog.getDialogTitle()).to.eq('wlbApp.issueWatcher.delete.question');
    await issueWatcherDeleteDialog.clickOnConfirmButton();

    expect(await issueWatcherComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
