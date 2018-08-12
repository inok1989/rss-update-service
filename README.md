# Tiny Tiny RSS Windows Update Service

A small windows service to trigger update on a [Tiny Tiny RSS server](https://tt-rss.org).

## How to use

1. Download the [latest release](https://github.com/inok1989/tt-rss-windows-update-service/releases) and unzip.
2. Configure your server
   * Add a file `application.properties`.
   * Add content: `de.kgrupp.ttrss.windowsupdateservice.url=[YOUR_TTRSS_SERVER]/public.php?op=globalUpdateFeeds&daemon=1`
3. Install the windows service via commandline:
   * `tt-rss-windows-update-service.exe install`
   * `tt-rss-windows-update-service.exe start`