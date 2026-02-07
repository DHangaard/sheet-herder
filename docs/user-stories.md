# User Stories
This document outlines the high-level user stories that define the functional scope of the application.<br>
The stories are intentionally broad and represent core use cases rather than fully decomposed tasks as the project is developed by a single contributor.<br>

## US-1: User Authentication
As a user, I want to log in to the application, so that I can access and manage my characters and campaigns.

**Acceptance criteria**<br>
**Given** I am not authenticated<br>
**When** I provide valid login credentials<br>
**Then** I gain access to my personal data and functionality<br>

---

## US-2: Create and Manage Characters
As a player, I want to create, edit, and delete characters, so that I can manage my tabletop role-playing characters digitally.

**Acceptance criteria**<br>
**Given** I am logged in<br>
**When** I create or update a character<br>
**Then** the character data is stored and reflected in my character overview<br>

**Given** I am logged in<br>
**When** I attempt to create or update a character with invalid input<br>
**Then** I receive clear validation feedback and the character is not persisted<br>

---

## US-3: Character Portrait Upload
As a player, I want to upload a portrait image for my character, so that I can visually represent my character.

**Acceptance criteria**<br>
**Given** I am creating or editing a character<br>
**When** I upload a valid image file<br>
**Then** the image is stored and associated with my character<br>

**Given** I am creating or editing a character<br>
**When** I upload an invalid image file<br>
**Then** I receive a clear validation error<br>

---

## US-4: Character Overview ("My Herd")
As a player, I want to see an overview of all my characters, so that I can easily manage and access them.

**Acceptance criteria**<br>
**Given** I have created one or more characters<br>
**When** I navigate to my character overview<br>
**Then** all my characters are displayed in a list or grid view<br>

---

## US-5: Character Notes
As a player, I want to add and edit notes on my character, so that I can keep track of story events and development.

**Acceptance criteria**<br>
**Given** I am viewing one of my characters<br>
**When** I add or update notes<br>
**Then** the notes are saved and associated with that character<br>

---

## US-6: Create Campaign
As a game master, I want to create a campaign, so that I can manage players, characters, and shared information.

**Acceptance criteria**<br>
**Given** I am logged in<br>
**When** I create a new campaign<br>
**Then** I become the campaign owner with administrative permissions<br>

---

## US-7: Campaign Participation (Player)
As a player, I want to join or leave a campaign, so that I can participate in or withdraw from a game.

**Acceptance criteria**<br>
**Given** I have been invited to a campaign<br>
**When** I accept or leave the campaign<br>
**Then** my access to campaign-related data is updated accordingly<br>

---

## US-8: Campaign Membership Management (Game Master)
As a game master, I want to manage players in my campaign, so that I can control who participates.

**Acceptance criteria**<br>
**Given** I am the game master of a campaign<br>
**When** I invite players or remove existing participants<br>
**Then** campaign membership is updated according to my actions<br>

**Given** a player uses a campaign invitation mechanism<br>
**When** the request is approved<br>
**Then** the player gains access to the campaign<br>

---

## US-9: Campaign Notes and Session Logs
As a game master, I want to create and maintain campaign notes and session logs, so that I can document the progression of the story.

**Acceptance criteria**<br>
**Given** I am managing a campaign<br>
**When** I create or update session logs<br>
**Then** the logs are available for campaign participants to read<br>

**Given** I am managing a campaign<br>
**When** I create or update campaign notes<br>
**Then** the notes are private and not accessible to campaign participants<br>

---

## US-10: View Detailed Character Data (Game Master)
As a game master, I want to access detailed information for all characters in my campaign, so that I can manage encounters and game progression.

**Acceptance criteria**<br>
**Given** I am the game master of a campaign<br>
**When** I access the campaign character overview<br>
**Then** I can access full character details for all characters participating in the campaign<br>

---

## US-11: View Campaign Participants (Player)
As a player, I want to view an overview of participants in my campaign, so that I can see who is playing.

**Acceptance criteria**<br>
**Given** I am a player participating in a campaign<br>
**When** I access the campaign overview<br>
**Then** I can view a list or card view of all participants with limited, player-visible information<br>

---

## US-12: Reference Data for Character Creation
As a user, I want access to predefined reference data during character creation, so that I can create characters more consistently and efficiently.

**Acceptance criteria**<br>
**Given** I am creating or editing a character<br>
**When** I select character attributes or traits<br>
**Then** I am presented with optional reference values to assist my input<br>

---

## US-13: Guest Character Creation (OPTIONAL)
_This user story represents an optional feature and is not part of the core MVP scope._<br><br>
As a guest user, I want to create a character and retrieve the character sheet, so that I can use it without creating an account.

**Acceptance criteria**<br>
**Given** I am using the application without being logged in<br>
**When** I create a character<br>
**Then** I am able to retrieve the character sheet for personal use without the data being stored in the system<br>

---