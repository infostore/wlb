import { element, by, ElementFinder } from 'protractor';

export class IssueLabelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-issue-label div table .btn-danger'));
  title = element.all(by.css('jhi-issue-label div h2#page-heading span')).first();
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

export class IssueLabelUpdatePage {
  pageTitle = element(by.id('jhi-issue-label-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  issueSelect = element(by.id('field_issue'));
  labelSelect = element(by.id('field_label'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async labelSelectLastOption(): Promise<void> {
    await this.labelSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async labelSelectOption(option: string): Promise<void> {
    await this.labelSelect.sendKeys(option);
  }

  getLabelSelect(): ElementFinder {
    return this.labelSelect;
  }

  async getLabelSelectedOption(): Promise<string> {
    return await this.labelSelect.element(by.css('option:checked')).getText();
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

export class IssueLabelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-issueLabel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-issueLabel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
