# string

A tool help you manipulate lines in shell.

```
> string --help
> string -v
```

## how to use

### read from stdin

```
string ${command} [arguments ...]
```

### read from shell argument

```
string ${command} [arguments ...] -- line1 [line2 line3 ...]
```

## example

```
> cat file
abcd
efgh
> cat file | sub -2 | join
abef
```

```
> reverse -- abcd
dcba
```

## alias

You may put the string executable file into your `$PATH`, then add the following to your `~/.bashrc`:

```
alias trim="string trim"
alias sub="string sub"
alias split="string split"
alias split-get="string split-get"
alias length="string length"
alias index="string index"
alias replace="string replace"
alias reverse="string reverse"
alias join="string join"
```

## indexes

Assume we have a string `abcd`. The indexes are as the following:

```
let ε = ""
------+---+---+---+---+---+------
    ε | ε | a | b | c | d | ε
------+---+---+---+---+---+------
  NaN   0   1   2   3   4   5 or other int > 5
           -4  -3  -2  -1  -0 (-0 represents the index after the last one)
```

It's considered that:

1. `γ == one character`
2. `ε == empty string`
3. `string == ε{infinite} γ* ε{infinite}`
4. indexes start from `1`
5. `-1` represents the last `γ` of a string
6. `0` represents the string before the first `γ`, whose corresponding value is empty string: `str[0] == ε`
7. `-0` represents the string after the last `γ`, whose corresponding value is empty string: `str[-0] == ε`
8. `ε` is always before or after `γ`s, there is no `ε`s among `γ`s
