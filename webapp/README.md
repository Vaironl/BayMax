Static assets that used to be on S3 need to be re-linked if the S3 objects go down from staticassets folder.

Also this server had phpmyadmin installed and you'll have to do that for a non-dead DB Administration deadlink.

Server was LEMP with HTTPS provided by Let's Encrypt.

The edison IP was "10.128.128.178" and is stormed in arms.js. Arms.js uses a weird <img> hack and that's because CORS would block the proper implementation.
