# SoleLink (Shoe Marketplace)

A JSF + Hibernate/JPA web app that connects fragmented shoe vendors in Rwanda
with buyers through a searchable catalog. Built as Assignment 3 for the
JSF course at AUCA.

Vendors currently market through TikTok, Instagram, and WhatsApp Status —
channels with no searchable structure. SoleLink gives vendors a simple way
to list shoe inventory (brand, size, price, condition) and gives buyers a
single place to browse listings instead of scrolling social feeds.

## Tech stack
- Java, Jakarta Faces (JSF)
- Hibernate ORM via JPA (`jakarta.persistence`)
- PostgreSQL
- Maven

## Entities
- **Vendor** — shopName, phone, email, location
- **Shoe** — brand, size, price, condition (New/Used) — belongs to a Vendor

## Validation
Three layers, as required by the assignment:
1. **Presentation (JSF)** — `required`, `f:validateRegex`, `f:validateLongRange`, `f:validateLength`
2. **Business logic (ManagedBean)** — duplicate-email check in `VendorBean`
3. **Database (Bean Validation / Hibernate)** — `@NotBlank`, `@Pattern`, `@Positive`, `nullable = false`

## Running locally
1. Create a PostgreSQL database named `shoemarket`.
2. Copy `src/main/resources/db.properties.example` to
   `src/main/resources/db.properties` and fill in your local credentials.
   This file is gitignored and never committed.
3. Build: `./mvnw clean package`
4. Deploy the generated `.war` (in `target/`) to Tomcat.
5. Visit `http://localhost:8080/ShoeMarketplace/`
