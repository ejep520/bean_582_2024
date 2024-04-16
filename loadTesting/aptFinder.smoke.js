import { browser } from 'k6/experimental/browser';

export const options = {
    scenarios: {
      browser: {
        executor: 'shared-iterations',
        options: {
          browser: {
              type: 'chromium',
          },
        },
      },
    },
  };

  //set "K6_BROWSER_HEADLESS=false" && set "K6_BROWSER_ARGS='show-property-changed-rects' " && k6 run aptFinder.smoke.js
const usernames = [];

export default function() {
    usernames.push('janedoe');
    const url = 'http://localhost:8080';
    const page = browser.newPage(options);
    //page.setViewportSize(1920, 1080);
    page.goto(url);
    page.waitForTimeout(2500);
    if (page.url == 'http://localhost:8080/newuser'){
        // New user
        // Username: <input slot="input" type="text" id="input-vaadin-text-field-17" aria-labelledby="label-vaadin-text-field-8">
        // Password1: <input slot="input" type="password" id="input-vaadin-password-field-18" autocapitalize="off" aria-labelledby="label-vaadin-password-field-11">
        // Password2: <input slot="input" type="password" id="input-vaadin-password-field-19" autocapitalize="off" aria-labelledby="label-vaadin-password-field-14">
        // Submit button: <vaadin-button tabindex="0" role="button">Send</vaadin-button>

        const txtUsername = page.locator('input[name="username"]');
        const password1 = page.locator('#input-vaadin-password-field-18');
        const password2 = page.locator('#input-vaadin-password-field-19');
        const submit = page.locator('vaadin-button[role="button"]');
        let username = random();
        txtUsername.type(username);
        usernames[usernames.length] = username;
        console.log('Username: ' + username);
        password1.type('password');
        password2.type('password');
        usernames.add(username);
        submit.click();
    }
    // Login
    var username = usernames.pop(); //'janedoe';
    console.log('Username: ' + username);
    page.locator('#vaadinLoginUsername').type(username);
    page.locator('#vaadinLoginPassword').type('password');
    page.locator('vaadin-button[slot="submit"]').click();
    page.waitForTimeout(2500);
    page.goto('http://localhost:8080/owner');
    page.waitForTimeout(2500);
    page.goto('http://localhost:8080/admin');
    page.waitForTimeout(2500);
    page.goto('http://localhost:8080/home');
    page.waitForTimeout(2500);
    page.goto('http://localhost:8080/newuser');
    newUser();
    page.waitForTimeout(2500);
}

const newUser = () => {
    const txtUsername = page.locator('input[name="username"]');
    const password1 = page.locator('#input-vaadin-password-field-18');
    const password2 = page.locator('#input-vaadin-password-field-19');
    const submit = page.locator('vaadin-button[role="button"]');
    let username = random();
    txtUsername.type(username);
    usernames[usernames.length] = username;
    console.log('Username: ' + username);
    password1.type('password');
    password2.type('password');
    usernames.add(username);
    submit.click();
}

const random = (length = 16) => {
    // Declare all characters
    let chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

    // Pick characers randomly
    let str = '';
    for (let i = 0; i < length; i++) {
        str += chars.charAt(Math.floor(Math.random() * chars.length));
    }

    return str;
};

