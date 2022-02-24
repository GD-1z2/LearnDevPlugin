# Learn Dev Plugin

A plugin for [Ordinal Bot](https://github.com/Ordinal-Team/OrdinalBot-API) that can display useful information about
programming languages

![](https://i.imgur.com/KtjljEl.png)

## Commands

### Display language list

```
!learn
```

### Show language info

```
!learn <language>
```
*Note : you can use the language's name as well as aliases*

### Add a language [(moderators only)](#set-plugin-moderators)

```
!ldconfig add <name>
```

### Delete a language [(moderators only)](#set-plugin-moderators)

```
!ldconfig del <name>
```

### Update a language's info [(moderators only)](#set-plugin-moderators)

```
!ldconfig set <language> name <NewName>

!ldconfig set <language> alias <NameAlias> <NameAlias2>...

!ldconfig set <language> desc <A new description>

!ldconfig set <language> img <https://i.imgur.com/5gkKbMp.png>

!ldconfig set <language> rsc <https://course1> <https://course2>...

!ldconfig set <language> authors <discord id 1> <discord id 2>...
```

## Config files

The language list is stored in `config/learndev/languages.json`.
The plugin configuration is stored in `config/learndev/config.json`.

## Set plugin moderators

Moderators have to be manually added in the config file

Example :
```json5
{
    "moderators": [
        "424242424242424242", /* Moderator 1 */
        "666666666666666666", /* Moderator 2 */
    ]
}
```
