import { element, by, ElementFinder } from 'protractor';

export class LabelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-label div table .btn-danger'));
  title = element.all(by.css('jhi-label div h2#page-heading span')).first();
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

export class LabelUpdatePage {
  pageTitle = element(by.id('jhi-label-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));

  labelGroupSelect = element(by.id('field_labelGroup'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async labelGroupSelectLastOption(): Promise<void> {
    await this.labelGroupSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async labelGroupSelectOption(option: string): Promise<void> {
    await this.labelGroupSelect.sendKeys(option);
  }

  getLabelGroupSelect(): ElementFinder {
    return this.labelGroupSelect;
  }

  async getLabelGroupSelectedOption(): Promise<string> {
    return await this.labelGroupSelect.element(by.css('option:checked')).getText();
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

export class LabelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-label-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-label'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
