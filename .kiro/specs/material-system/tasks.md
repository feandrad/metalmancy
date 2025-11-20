# Plano de Implementação - Sistema de Materiais do Metalmancy

- [ ] 1. Implementar sistema core de materiais
  - Criar estruturas de dados para Material, Family e Part
  - Implementar lógica de geração de nomes não-localizados
  - Criar catálogo de materiais em Materials.kt
  - _Requisitos: 1.1, 1.2, 1.3, 1.4, 1.5_

- [ ]* 1.1 Escrever testes de propriedade para Material
  - **Propriedade 1: Preservação de campos do Material**
  - **Valida: Requisitos 1.1, 1.5**

- [ ]* 1.2 Escrever testes de propriedade para geração de nomes
  - **Propriedade 2: Geração correta de nomes não-localizados**
  - **Valida: Requisitos 1.2**

- [ ]* 1.3 Escrever testes de propriedade para agrupamento
  - **Propriedade 3: Agrupamento por família**
  - **Propriedade 4: Completude da lista ALL**
  - **Valida: Requisitos 1.4, 10.1, 10.2, 10.5**

- [ ] 2. Implementar sistema de registro de blocos
  - Criar MaterialBlocks.kt com lógica de registro
  - Implementar createLike para copiar propriedades de blocos vanilla
  - Organizar blocos por categoria (GEMS, SALTS, METALS)
  - Aplicar propriedades específicas baseadas em categoria
  - _Requisitos: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 10.4_

- [ ]* 2.1 Escrever testes de propriedade para registro de blocos
  - **Propriedade 5: Criação de blocos para partes de bloco**
  - **Propriedade 6: Namespace correto em ResourceLocations de blocos**
  - **Propriedade 32: Aplicação de propriedades baseadas em categoria**
  - **Valida: Requisitos 2.1, 2.6, 2.5, 10.4**

- [ ]* 2.2 Escrever testes unitários para propriedades de blocos
  - Testar que blocos de gemas usam EMERALD_ORE como base
  - Testar que blocos de sais usam COAL_ORE como base
  - Testar aplicação correta de propriedades por categoria de metal
  - _Requisitos: 2.3, 2.4, 2.5_

- [ ] 3. Implementar sistema de registro de itens
  - Criar MaterialItems.kt com lógica de registro
  - Implementar mapItems para criar itens e BlockItems
  - Garantir uso de useBlockDescriptionPrefix para BlockItems
  - Mapear todas as partes de materiais para itens
  - _Requisitos: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ]* 3.1 Escrever testes de propriedade para registro de itens
  - **Propriedade 7: Criação de itens para partes de item**
  - **Propriedade 8: Criação de BlockItems para blocos**
  - **Propriedade 9: Namespace correto em ResourceLocations de itens**
  - **Propriedade 10: Mapeamento completo de partes para itens**
  - **Valida: Requisitos 3.1, 3.2, 3.4, 3.5**

- [ ]* 3.2 Escrever testes unitários para BlockItems
  - Testar que BlockItems usam descrição do bloco como prefixo
  - _Requisitos: 3.3_

- [ ] 4. Implementar Block Generator
  - Criar estrutura GeneratedBlock abstrata
  - Implementar DefaultBlock para blocos cúbicos
  - Criar BlockEntries que itera sobre Materials.ALL
  - Implementar geração de blockstate JSON
  - Implementar geração de block model JSON
  - Adicionar lógica de criação automática de diretórios
  - Implementar pretty-printing de JSON
  - _Requisitos: 4.1, 4.2, 4.3, 4.4_

- [ ]* 4.1 Escrever testes de propriedade para Block Generator
  - **Propriedade 11: Geração de blockstate para cada bloco**
  - **Propriedade 12: Geração de modelo de bloco para cada bloco**
  - **Propriedade 13: Pretty-printing de JSON**
  - **Propriedade 14: Criação automática de diretórios**
  - **Valida: Requisitos 4.1, 4.2, 4.3, 4.4**

- [ ]* 4.2 Escrever testes de validação de JSON
  - Testar que JSON gerado é válido e parseável
  - Testar estrutura de blockstate (variants, model)
  - Testar estrutura de block model (parent, textures)
  - _Requisitos: 4.1, 4.2_

- [ ] 5. Implementar Item Generator
  - Criar estrutura GeneratedItem
  - Criar GeneratedBlockItem que referencia modelos de blocos
  - Criar ItemEntries que itera sobre Materials.ALL
  - Implementar geração de item model JSON
  - Implementar geração de item render JSON
  - Processar blocos para gerar modelos de item correspondentes
  - _Requisitos: 5.1, 5.2, 5.3, 5.4_

- [ ]* 5.1 Escrever testes de propriedade para Item Generator
  - **Propriedade 15: Geração de modelo de item para cada item**
  - **Propriedade 16: Geração de renderização de item**
  - **Propriedade 17: Modelos de item para blocos**
  - **Valida: Requisitos 5.1, 5.2, 5.3**

- [ ]* 5.2 Escrever testes de validação de JSON de itens
  - Testar que JSON de item model é válido
  - Testar que JSON de item render é válido
  - Testar que BlockItems referenciam modelos de blocos corretamente
  - _Requisitos: 5.1, 5.2, 5.3_

- [ ] 6. Implementar Recipe Generator
  - Criar estrutura GeneratedRecipe abstrata
  - Implementar SmeltingRecipe e BlastingRecipe
  - Criar Recipes.kt com lógica byFamily
  - Implementar geração de receitas para metais (ORE → INGOT)
  - Implementar geração de receitas para gemas (ORE → GEM)
  - Criar RecipeEntries que itera sobre Materials.ALL
  - Configurar Gson com disableHtmlEscaping
  - _Requisitos: 6.1, 6.2, 6.3, 6.4_

- [ ]* 6.1 Escrever testes de propriedade para Recipe Generator
  - **Propriedade 18: Geração de receita para cada entrada**
  - **Propriedade 19: Desabilitação de escape HTML em receitas**
  - **Valida: Requisitos 6.1, 6.3**

- [ ]* 6.2 Escrever testes unitários para receitas
  - Testar geração de smelting recipes para metais
  - Testar geração de blasting recipes para metais
  - Testar geração de smelting recipes para gemas
  - Testar que receitas incluem variantes deepslate
  - _Requisitos: 6.1_

- [ ] 7. Implementar Worldgen Generator
  - Criar estrutura OreGen data class
  - Criar enum OreGenHeightType (TRAPEZOID, TRIANGLE, UNIFORM)
  - Criar OreGenEntries com configurações para overworld/nether/ender
  - Implementar geração de configured_feature JSON
  - Implementar geração de placed_feature JSON
  - Adicionar suporte para targets stone e deepslate
  - Implementar lógica de sufixos para nomes duplicados
  - _Requisitos: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7_

- [ ]* 7.1 Escrever testes de propriedade para Worldgen Generator
  - **Propriedade 20: Geração de configured_feature para minérios**
  - **Propriedade 21: Geração de placed_feature para minérios**
  - **Propriedade 22: Suporte a variantes stone e deepslate**
  - **Propriedade 23: Respeito à faixa de altura Y**
  - **Propriedade 24: Suporte a tipos de distribuição**
  - **Propriedade 25: Inclusão de veinSize e countPerChunk**
  - **Propriedade 26: Unicidade de nomes de features**
  - **Valida: Requisitos 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7**

- [ ]* 7.2 Escrever testes de validação de worldgen JSON
  - Testar estrutura de configured_feature
  - Testar estrutura de placed_feature
  - Testar que yRange está dentro de limites válidos (-64..320)
  - _Requisitos: 7.1, 7.2, 7.4_

- [ ] 8. Configurar integração com Gradle
  - Criar task generateJson para Block Generator
  - Criar task generateJson para Item Generator
  - Criar task generateJson para Recipe Generator
  - Criar task generateJson para Worldgen Generator
  - Adicionar suporte para argumento --out
  - Criar task syncGeneratedWorldgen para copiar assets
  - Adicionar src/tools ao source set principal
  - _Requisitos: 8.1, 8.2, 8.4, 8.5_

- [ ]* 8.1 Escrever testes de propriedade para parsing de argumentos
  - **Propriedade 27: Parsing de argumentos de linha de comando**
  - **Valida: Requisitos 8.2**

- [ ]* 8.2 Escrever testes de integração para tasks Gradle
  - Testar execução de generateJson tasks
  - Testar que arquivos são gerados no diretório correto
  - Testar que syncGeneratedWorldgen copia arquivos
  - _Requisitos: 8.1, 8.4_

- [ ] 9. Implementar suporte multi-plataforma
  - Criar interface PlatformHelper
  - Implementar Metalmancy.init() para aceitar PlatformHelper
  - Garantir uso de ResourceLocation.fromNamespaceAndPath()
  - Garantir uso de BuiltInRegistries
  - Criar módulo Fabric com implementação específica
  - Estruturar módulo NeoForge (implementação pendente)
  - _Requisitos: 9.1, 9.2, 9.3, 9.4, 9.5_

- [ ]* 9.1 Escrever testes de propriedade para platform abstraction
  - **Propriedade 28: Armazenamento de PlatformHelper**
  - **Propriedade 29: Uso de registries compatíveis**
  - **Propriedade 30: Criação de ResourceLocation compatível com 1.21.x**
  - **Valida: Requisitos 9.1, 9.4, 9.5**

- [ ]* 9.2 Escrever testes de integração para Fabric
  - Testar inicialização do mod no Fabric
  - Testar registro de blocos e itens
  - Testar carregamento de assets
  - _Requisitos: 9.2, 9.3_

- [ ] 10. Implementar organização de materiais por categoria
  - Criar listas COPPER_LIKE_METALS, IRON_LIKE_METALS, etc.
  - Garantir que METALS é união de todas as categorias
  - Garantir que ALL é união de GEMS + SALTS + METALS
  - Aplicar propriedades de blocos baseadas em categoria
  - _Requisitos: 10.1, 10.2, 10.3, 10.4, 10.5_

- [ ]* 10.1 Escrever testes de propriedade para categorização
  - **Propriedade 31: Agrupamento de metais por nível**
  - **Valida: Requisitos 10.3**

- [ ] 11. Checkpoint - Garantir que todos os testes passam
  - Garantir que todos os testes passam, perguntar ao usuário se surgirem questões.

- [ ] 12. Adicionar materiais ao catálogo
  - Adicionar definições de gemas (Ruby, Sapphire, Topaz)
  - Adicionar definições de sais (Salt, Potash)
  - Adicionar definições de metais base (Zinc, Tin, Lead, Nickel)
  - Adicionar definições de metais intermediários (Aluminum, Manganese, Silver, Cobalt)
  - Adicionar definições de metais avançados (Platinum, Titanium, Lithium, Uranium)
  - Adicionar definições de metais místicos (Mithril, Orichalcum)
  - Adicionar definições de ligas (Pewter, Brass, Bronze, Steel, Electrum, Invar)
  - Adicionar Mercury (Cinnabar)
  - _Requisitos: 1.1, 1.4, 10.1, 10.2, 10.3, 10.5_

- [ ]* 12.1 Escrever testes unitários para materiais específicos
  - Testar que cada material tem as partes corretas
  - Testar que materiais estão nas listas corretas
  - _Requisitos: 1.1, 1.4_

- [ ] 13. Configurar geração de worldgen para materiais
  - Configurar OreGen para gemas (Ruby, Sapphire, Topaz)
  - Configurar OreGen para sais (Salt, Potash)
  - Configurar OreGen para metais base
  - Configurar OreGen para metais intermediários
  - Configurar OreGen para metais avançados
  - Configurar OreGen para Mithril (Nether)
  - Configurar OreGen para Orichalcum (End)
  - Configurar múltiplas variantes com sufixos onde necessário
  - _Requisitos: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7_

- [ ]* 13.1 Escrever testes de validação para configurações de worldgen
  - Testar que todos os materiais com ORE têm configuração de worldgen
  - Testar que configurações estão dentro de limites válidos
  - _Requisitos: 7.4_

- [ ] 14. Checkpoint final - Garantir que todos os testes passam
  - Garantir que todos os testes passam, perguntar ao usuário se surgirem questões.
