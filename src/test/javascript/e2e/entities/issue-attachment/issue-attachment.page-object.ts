import { element, by, ElementFinder } from 'protractor';

export class IssueAttachmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-issue-attachment div table .btn-danger'));
  title = element.all(by.css('jhi-issue-attachment div h2#page-heading span')).first();
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

export class IssueAttachmentUpdatePage {
  pageTitle = element(by.id('jhi-issue-attachment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  attachmentSelect = element(by.id('field_attachment'));
  issueSelect = element(by.id('field_issue'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async attachmentSelectLastOption(): Promise<void> {
    await this.attachmentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async attachmentSelectOption(option: string): Promise<void> {
    await this.attachmentSelect.sendKeys(option);
  }

  getAttachmentSelect(): ElementFinder {
    return this.attachmentSelect;
  }

  async getAttachmentSelectedOption(): Promise<string> {
    return await this.attachmentSelect.element(by.css('option:checked')).getText();
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

export class IssueAttachmentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-issueAttachment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-issueAttachment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
