Playwright Framework Guidelines & Code Quality Standards
1. Project Structure
Follow a clear, modular folder structure:
├── api-calls/        # API endpoints  
├── features/         # Feature files (per module)  
├── pages/            # Page Object Models (POMs)  
├── step-definitions/ # Step definition files (end with .def.ts)  
├── fixtures/         # Test data / mock data (country-specific like sg, hk)  
✅ Keep test logic separate from locators and utilities.
2. Naming Conventions
Files/Folders → camelCase
Classes → PascalCase
Page Objects → End with Page.ts
Step Definitions → End with .def.ts
Examples:
loginPage.ts
customerDetailsPage.ts
srValidator.ts
3. Classes & Methods
Classes → PascalCase (e.g., LoginPage, SRValidator)
Functions/Methods → camelCase (e.g., clickOnLoginButton)
export class PageObjects {
  public get loginPage() { return new LoginPage(); }
  public get productAndServicesPage() { return new ProductAndServicesPage(); }
  public get navigationPage() { return new NavigationPage(); }
  public get globalSearchPage() { return new GlobalSearchPage(); }
}
4. Locators
Use UPPER_SNAKE_CASE for locator objects and keys.
Keep all locators in objects, never hard-coded inside tests.
Define locators at the top of Page Object files.
export const SEARCH_SR_LOCATORS = {
  srInput: 'srInput',
  searchResultTable: 'sr-global-search-listing-table-style',
  srNumberRadioButtonContainer: 'radio-button-container',
  searchResultFirstRow: 'tbody > tr[role="row"]'
};
5. Text Constants
Store UI text in a TEXT_CONSTANTS object.
Use UPPER_SNAKE_CASE for both keys and values.
export const TEXT_CONSTANTS = {
  SEARCH_SR_LINK: 'Search SR',
  CREATE_CASE_BUTTON: 'Create Case',
  COMMENTS_LABEL: 'Comments'
};
6. Assertions
Use Playwright expect API:
await expect(locator).toBeVisible();
❌ Avoid manual checks:
if (await locator.isVisible()) === true
7. Selectors & Locators
Prefer data-testid → stable and independent of UI changes.
Fallback: getByRole, getByText, .filter()
❌ Avoid brittle selectors (CSS, XPath, nth-child, deep DOM chains).
Example:
page.getByTestId('search-button');
page.getByRole('button', { name: /Search/i });
8. Error Handling
Wrap reusable actions with safe error handling:
async safeClick(locator: Locator) {
  await locator.waitFor({ state: "visible" });
  await locator.click();
}
9. Code Quality Rules
✅ Avoid copy-paste → extract helper functions
✅ Reuse page methods, don’t duplicate locators
✅ Always add return types (Promise<void>, Promise<string>)
✅ Use async/await consistently
❌ Don’t use hard waits (waitForTimeout)
✅ Prefer waitForLoadState or expect().toBeVisible()
✅ Keep test data in fixtures/constants, not in test steps
10. Reusable Utilities
Create utilities for:
API calls (apiService.ts)
Date/time formatting
Random data generation (e.g., faker.js)
11. Reviews & Standards
Every PR should include:
✅ Proper commit message with JIRA ID
✅ No hardcoded values (unless test-specific)
✅ Proper use of POM & constants
✅ Linting/Prettier applied
12. BDD (if using Cucumber)
Step definitions should call Page methods, not raw selectors.
Feature files should be business-readable, not technical.
Feature file naming convention:
Start with squad name → Squad_<Name>_<Module>_Feature
Example Feature File:
@serviceRequests_Feature
Feature: Squad_Jatayu_Create_SR_end_to_end_flow
  Scenario Outline: SR Creation end to end with Iserve Widget for "<Country>"
    Given I am on client connect home page by logging in through "<LoginUser>"
    When I click on "Service request" link in the sidebar
    ...
Each scenario should have country-based tags (e.g., @SG, @HK).
❌ Don’t commit code with @wip or temporary tags.
13. Jenkins Integration
Maintain Jenkins job links for Playwright test execution.
14. Reporting
Integrate with Report Portal for dashboards and execution results.
