# onetab-to-pinboard

A quick lunchtime cli script inspired by [a Hacker News comment](https://news.ycombinator.com/item?id=7543868).

> GVRV 1 day ago | link | parent | flag
>
> It provides an export as a text file on the 'One Tab' page in the right hand corner. I wrote a small Python script to parse this text file and upload all my tabs to Pinboard.

It uploads urls from a [OneTab](https://chrome.google.com/webstore/detail/onetab/chphlpgkkbolifaimnlloiipkdnihall?hl=en) url export to your Pinboard account.

You can find the url list by clicking "Export / Import URLs" on the top-right of the OneTab extension's home screen.

## Setup

Install Leiningen (Clojure's build tool):

> 1. Make sure you have a Java JDK version 6 or later.
> 2. [Download the script](https://raw.github.com/technomancy/leiningen/stable/bin/lein).
> 3. Place it on your $PATH. (~/bin is a good choice if it is on your path.)
> 4. Set it to be executable. (chmod 755 ~/bin/lein)

Grab the project source:

    $ git clone git@github.com:danneu/onetab-to-pinboard.git
    $ cd onetab-to-pinboard

Generate the stand-alone executable:

    $ lein bin
    Compiling onetab-to-pinboard.core
    Creating standalone executable: /Users/danneu/Code/Clojure/onetab-to-pinboard/target/onetab-to-pinboard

## Usage

Hand the executable your [Pinboard auth token](https://pinboard.in/settings/password) and a path to your OneTab url dump:

    ./onetab-to-pinboard --token danneu:AABBCCDDEEFFAABBCCDD \
                         --file  ../resources/url-export.txt

    Found 14 bookmarks to submit.

    => https://github.com/dakrone/clj-http
    <= {:body {:result_code "done"}, :status 200, :request-time 8637}
    ...
    => https://news.ycombinator.com/item?id=7542925
    <= {:body {:result_code "done"}, :status 200, :request-time 437}
    ...
