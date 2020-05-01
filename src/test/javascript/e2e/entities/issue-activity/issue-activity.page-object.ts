import { element, by, ElementFinder } from 'protractor';

export class IssueActivityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-issue-activity div table .btn-danger'));
  title = element.all(by.css('jhi-issue-activity div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class IssueActivityUpdatePage {
  pageTitle = element(by.id('jhi-issue-activity-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  activityInput = element(by.id('field_activity'));

  issueSelect = element(by.id('field_issue'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setActivityInput(activity: string): Promise<void> {
    await this.activityInput.sendKeys(activity);
  }

  async getActivityInput(): Promise<string> {
    return await this.activityInput.getAttribute('value');
  }

  async issueSelectLastOption(): Promise<void> {
    await this.issueSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async issueSelectOption(option: string): Promise<void> {
    await this.issueSelect.sendKeys(option);
  }

  getIssueSelect(): ElementFinder {
    return this.issueSelect;
  }

  async getIssueSelectedOption(): Promise<string> {
    return await this.issueSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class IssueActivityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-issueActivity-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-issueActivity'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
