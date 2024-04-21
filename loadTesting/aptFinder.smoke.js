import { browser } from 'k6/experimental/browser';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";

export const options = {
    scenarios: {
      browser: {
        executor: 'shared-iterations',
        options: {
          browser: {
              type: 'chromium',
          },
        },
        vus: 10,
        iterations: 40,
      },
    },
  };

// https://github.com/benc-uk/k6-reporter?tab=readme-ov-file
export function handleSummary(data) {
    return {
        "LoadTestSummary.html": htmlReport(data, {title: "Apartment Finder Load Test"}),
        stdout: textSummary(data, { indent: " ", enableColors: true }),
    };
}

//set "K6_BROWSER_HEADLESS=false" && set "K6_BROWSER_ARGS='show-property-changed-rects' " && k6 run aptFinder.smoke.js
// Create janedoe user with password set to password before running this script
const usernames = [];

export default function() {
    usernames.push('janedoe');
    const url = 'http://localhost:8080';
    const page = browser.newPage(options);
    page.goto(url);
    page.waitForTimeout(2500);

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
    newUser(page);
    page.waitForTimeout(2500);
}

const newUser = (page) => {
    const txtUsername = page.locator('vaadin-text-field[data-testid="username"]');
    const password1 = page.locator('vaadin-password-field[data-testid="password1"]');
    const password2 = page.locator('vaadin-password-field[data-testid="password2"]');
    const submit = page.locator('vaadin-button[data-testid="send"]');
    let username = random();
    txtUsername.type(username);
    usernames[usernames.length] = username;
    console.log('Username: ' + username);
    password1.type('password');
    password2.type('password');
    usernames.push(username);
    page.waitForTimeout(2500);
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

