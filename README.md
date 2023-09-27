# mobile-architect-programming

## Overview

The Inventory Management App is a mobile application designed to help users efficiently manage their inventory in warehouses, storage facilities, or any context where inventory tracking is essential. It offers a user-friendly interface for adding, removing, updating, and viewing items, along with optional SMS notifications for low inventory items.

## Features

- **Inventory Tracking:** Easily add, remove, and update items in your inventory.
- **User Authentication:** Secure login functionality and account creation for new users.
- **SMS Notifications:** Receive optional SMS notifications for low inventory items.
- **Database Persistence:** User data is stored persistently in a local SQLite database.

## Android Version Compatibility

The app is designed to run on Android devices with a minimum Android version of Android 6.0 (Marshmallow) and above. This version choice covers a significant portion of Android users while still providing access to relevant APIs and features.

## Permissions

The app requests the following permissions, ensuring they are necessary for the app's core functionality:

- **SMS (for notifications):** Requested permission to send SMS notifications when the user enables this feature. Users will be informed that this permission is specifically for sending notifications.

## Usage

1. Launch the app.
2. Log in with your existing account or create a new one.
3. Use the app to manage your inventory, add items, remove items, and update item details.
4. Enable SMS notifications for low inventory items in the settings (optional).
