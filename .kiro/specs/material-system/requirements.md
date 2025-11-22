# Documento de Requisitos - Sistema de Materiais do Metalmancy

## Introdução

O Metalmancy é um mod de Minecraft que adiciona um sistema extensível de materiais ao jogo, incluindo novos metais, gemas e sais. O sistema utiliza uma arquitetura baseada em dados que permite a geração automática de blocos, itens, receitas e geração de mundo através de ferramentas de build-time. O mod é compatível com Fabric e NeoForge através do framework Architectury.

## Glossário

- **Material System**: O sistema central que define e gerencia todos os materiais do mod
- **Material**: Uma substância do jogo (metal, gema, sal) com propriedades e partes específicas
- **Part**: Uma forma específica de um material (minério, lingote, pepita, gema, pó, bloco)
- **Family**: Categoria de material (METAL, ALLOY, GEM, SALT, STONE, BONE, FABRIC)
- **Block Generator**: Ferramenta de build-time que gera arquivos JSON de blockstates e modelos
- **Item Generator**: Ferramenta de build-time que gera arquivos JSON de modelos de itens
- **Recipe Generator**: Ferramenta de build-time que gera arquivos JSON de receitas
- **Worldgen Generator**: Ferramenta de build-time que gera arquivos JSON de geração de mundo
- **Architectury**: Framework que permite compatibilidade entre Fabric e NeoForge
- **Minecraft Registry**: Sistema de registro do Minecraft para blocos, itens e outros objetos do jogo

## Requisitos

### Requisito 1: Definição de Materiais

**User Story:** Como desenvolvedor do mod, eu quero definir materiais com suas propriedades e partes, para que o sistema possa gerar automaticamente todos os recursos necessários.

#### Critérios de Aceitação

1. WHEN um material é definido THEN o sistema SHALL armazenar seu nome, família e conjunto de partes
2. WHEN um material possui uma parte específica THEN o sistema SHALL gerar o nome não-localizado correto para essa combinação
3. WHEN uma parte é definida THEN o sistema SHALL indicar se ela representa um bloco ou item
4. WHEN materiais são agrupados por família THEN o sistema SHALL permitir acesso a grupos como GEMS, SALTS, METALS
5. WHEN um material é consultado THEN o sistema SHALL fornecer acesso a todas as suas partes configuradas

#### Materiais Definidos

O sistema SHALL incluir os seguintes materiais:

**Gemas (3):**
- Ruby (rubi) - minério, minério deepslate, gema, bloco
- Sapphire (safira) - minério, minério deepslate, gema, bloco
- Topaz (topázio) - minério, minério deepslate, gema, bloco

**Metais Base - Nível Cobre (4):**
- Zinc (zinco) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Tin (estanho) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Lead (chumbo) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Nickel (níquel) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco

**Metais Intermediários - Nível Ferro (4):**
- Aluminum (alumínio) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Manganese (manganês) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Silver (prata) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Cobalt (cobalto) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco

**Metais Avançados - Nível Diamante (4):**
- Platinum (platina) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Titanium (titânio) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Lithium (lítio) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Uranium (urânio) - minério, minério deepslate, item bruto, bloco bruto, lingote, pepita, pó, bloco

**Metais Místicos - Nível Netherite (2):**
- Mithril - minério, item bruto, bloco bruto, lingote, pepita, pó, bloco
- Orichalcum - minério, item bruto, bloco bruto, lingote, pepita, pó, bloco

**Ligas (6):**
- Pewter (peltre) - lingote, pepita, pó, bloco
- Brass (latão) - lingote, pepita, pó, bloco
- Bronze - lingote, pepita, pó, bloco
- Steel (aço) - lingote, pepita, pó, bloco
- Electrum (electro) - lingote, pepita, pó, bloco
- Invar - lingote, pepita, pó, bloco

**Alchemy (3):**
- RockSalt (sal-gema) - minério, minério deepslate, gema, pó, bloco
- Potash (potássio) - minério, minério deepslate, item bruto, pó, bloco
- Mercury (cinábrio/minério de mercúrio) - minério, minério deepslate, pó

**Total: 26 materiais** (3 gemas + 3 Alchemy + 14 metais + 6 ligas)

### Requisito 2: Registro de Blocos

**User Story:** Como desenvolvedor do mod, eu quero registrar blocos de materiais automaticamente, para que todos os minérios e blocos sejam adicionados ao jogo.

#### Critérios de Aceitação

1. WHEN um material possui partes de bloco THEN o sistema SHALL criar e registrar blocos para cada parte
2. WHEN um bloco é criado THEN o sistema SHALL usar propriedades baseadas em blocos similares
6. WHEN um bloco é registrado THEN o sistema SHALL usar o ResourceLocation correto com o namespace do mod

### Requisito 3: Registro de Itens

**User Story:** Como desenvolvedor do mod, eu quero registrar itens de materiais automaticamente, para que lingotes, pepitas, gemas e outros itens sejam adicionados ao jogo.

#### Critérios de Aceitação

1. WHEN um material possui partes de item THEN o sistema SHALL criar e registrar itens para cada parte
2. WHEN um material possui blocos THEN o sistema SHALL criar BlockItems correspondentes
3. WHEN um BlockItem é criado THEN o sistema SHALL usar a descrição do bloco como prefixo
4. WHEN um item é registrado THEN o sistema SHALL usar o ResourceLocation correto com o namespace do mod
5. WHEN materiais específicos são inicializados THEN o sistema SHALL mapear corretamente todas as suas partes para itens

### Requisito 4: Geração de Assets de Blocos

**User Story:** Como desenvolvedor do mod, eu quero gerar automaticamente blockstates e modelos de blocos, para que não precise criar manualmente arquivos JSON repetitivos.

#### Critérios de Aceitação

1. WHEN o Block Generator é executado THEN o sistema SHALL gerar arquivos JSON de blockstate para cada bloco
2. WHEN o Block Generator é executado THEN o sistema SHALL gerar arquivos JSON de modelo de bloco para cada bloco
3. WHEN arquivos são gerados THEN o sistema SHALL usar formatação JSON com pretty-printing
4. WHEN arquivos são gerados THEN o sistema SHALL criar os diretórios necessários automaticamente
5. WHEN a geração é concluída THEN o sistema SHALL reportar o caminho de saída e arquivos criados

### Requisito 5: Geração de Assets de Itens

**User Story:** Como desenvolvedor do mod, eu quero gerar automaticamente modelos de itens, para que não precise criar manualmente arquivos JSON repetitivos.

#### Critérios de Aceitação

1. WHEN o Item Generator é executado THEN o sistema SHALL gerar arquivos JSON de modelo de item para cada item
2. WHEN o Item Generator é executado THEN o sistema SHALL gerar arquivos JSON de renderização de item
3. WHEN itens de blocos são processados THEN o sistema SHALL gerar modelos de item correspondentes
4. WHEN arquivos são gerados THEN o sistema SHALL usar formatação JSON com pretty-printing
5. WHEN a geração é concluída THEN o sistema SHALL reportar o caminho de saída e arquivos criados

### Requisito 6: Geração de Receitas

**User Story:** Como desenvolvedor do mod, eu quero gerar automaticamente receitas de crafting, para que os jogadores possam criar e processar materiais.

#### Critérios de Aceitação

1. WHEN o Recipe Generator é executado THEN o sistema SHALL gerar arquivos JSON de receita para cada entrada
2. WHEN arquivos são gerados THEN o sistema SHALL usar formatação JSON com pretty-printing
3. WHEN arquivos são gerados THEN o sistema SHALL desabilitar escape de HTML
4. WHEN arquivos são gerados THEN o sistema SHALL criar os diretórios necessários automaticamente
5. WHEN a geração é concluída THEN o sistema SHALL reportar o caminho de saída e arquivos criados

### Requisito 7: Geração de Worldgen

**User Story:** Como desenvolvedor do mod, eu quero gerar automaticamente configurações de geração de mundo, para que minérios apareçam naturalmente no mundo.

#### Critérios de Aceitação

1. WHEN o Worldgen Generator é executado THEN o sistema SHALL gerar configured_feature JSON para cada minério
2. WHEN o Worldgen Generator é executado THEN o sistema SHALL gerar placed_feature JSON para cada minério
3. WHEN minérios são configurados THEN o sistema SHALL suportar variantes de pedra normal e deepslate
4. WHEN minérios são configurados THEN o sistema SHALL permitir especificar faixa de altura Y
5. WHEN minérios são configurados THEN o sistema SHALL suportar tipos de distribuição (TRAPEZOID, TRIANGLE, UNIFORM)
6. WHEN minérios são configurados THEN o sistema SHALL permitir especificar tamanho de veio e contagem por chunk
7. WHEN nomes de features são duplicados THEN o sistema SHALL adicionar sufixos numéricos para garantir unicidade

### Requisito 8: Integração com Build System

**User Story:** Como desenvolvedor do mod, eu quero executar geradores através de tasks Gradle, para que a geração de assets seja parte do processo de build.

#### Critérios de Aceitação

1. WHEN tasks Gradle são definidas THEN o sistema SHALL permitir execução de geradores via linha de comando
2. WHEN geradores são executados THEN o sistema SHALL aceitar parâmetros de linha de comando para diretório de saída
3. WHEN geradores são executados THEN o sistema SHALL ter acesso ao classpath completo do projeto
4. WHEN assets são gerados THEN o sistema SHALL permitir cópia automática para diretório de resources
5. WHEN o projeto é compilado THEN o sistema SHALL incluir código de ferramentas no source set principal

### Requisito 9: Compatibilidade Multi-Plataforma

**User Story:** Como desenvolvedor do mod, eu quero suportar Fabric e NeoForge, para que jogadores de ambas as plataformas possam usar o mod.

#### Critérios de Aceitação

1. WHEN o mod é inicializado THEN o sistema SHALL aceitar um PlatformHelper específico da plataforma
2. WHEN recursos são criados THEN o sistema SHALL usar APIs do Architectury que funcionam em ambas plataformas
3. WHEN o projeto é estruturado THEN o sistema SHALL ter módulos separados para common, fabric e neoforge
4. WHEN blocos e itens são registrados THEN o sistema SHALL usar registries do Minecraft de forma compatível
5. WHEN ResourceLocations são criados THEN o sistema SHALL usar métodos compatíveis com a versão 1.21.x

### Requisito 10: Organização de Materiais por Categoria

**User Story:** Como desenvolvedor do mod, eu quero organizar materiais em categorias, para que seja fácil aplicar propriedades e comportamentos específicos.

#### Critérios de Aceitação

1. WHEN materiais são definidos THEN o sistema SHALL agrupar gemas em uma lista GEMS
2. WHEN materiais são definidos THEN o sistema SHALL agrupar sais em uma lista SALTS
3. WHEN materiais metálicos são definidos THEN o sistema SHALL agrupá-los por nível de mineração (copper-like, iron-like, gold-like, diamond-like)
4. WHEN blocos são criados THEN o sistema SHALL aplicar propriedades baseadas na categoria do material
5. WHEN todos os materiais são consultados THEN o sistema SHALL fornecer uma lista ALL contendo todas as categorias
