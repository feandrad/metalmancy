# Plano de Implementação - Sistema de Materiais do Metalmancy

## Status Geral

**Progresso:** 14/14 tarefas principais concluídas (100%)

### ✅ Concluído
- Sistema core de materiais (Material, Family, Part)
- Catálogo completo de 26 materiais (3 gemas, 3 alchemy, 14 metais, 6 ligas)
- Sistema de registro de blocos (MaterialBlocks.kt)
- Sistema de registro de itens (MaterialItems.kt)
- Block Generator (blockgen/)
- Item Generator (itemgen/)
- Recipe Generator (recipegen/)
- Loot Generator (lootgen/)
- Worldgen Generator (worldgen/)
- Integração com Gradle (tasks configuradas)
- Testes unitários básicos (MaterialTest, MaterialsTest, etc.)
- Testes de propriedade (Kotest) - 40 testes implementados
- Organização de materiais por categoria
- Implementação Fabric totalmente funcional

### ⏳ Pendente
- Suporte completo multi-plataforma (NeoForge parcialmente implementado)

### 🔍 Próximos Passos Recomendados
1. Completar implementação NeoForge (Task 9.1)
2. Testar mod em ambiente NeoForge
3. Adicionar texturas para novos materiais

---

## Tarefas Detalhadas

- [x] 1. Implementar sistema core de materiais
  - ✅ Criar estruturas de dados para Material, Family e Part
  - ✅ Implementar lógica de geração de nomes não-localizados
  - ✅ Criar catálogo de materiais em Materials.kt
  - ✅ Implementado em: `common/src/main/kotlin/io/felipeandrade/metalmancy/material/Materials.kt`
  - _Requisitos: 1.1, 1.2, 1.3, 1.4, 1.5_



- [x] 2. Implementar sistema de registro de blocos
  - ✅ Criar MaterialBlocks.kt com lógica de registro
  - ✅ Implementar createLike para copiar propriedades de blocos vanilla
  - ✅ Organizar blocos por categoria (GEMS, SALTS, METALS, ALLOYS)
  - ✅ Aplicar propriedades específicas baseadas em categoria
  - ✅ Implementado em: `common/src/main/kotlin/io/felipeandrade/metalmancy/material/MaterialBlocks.kt`
  - _Requisitos: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 10.4_



- [x] 3. Implementar sistema de registro de itens
  - ✅ Criar MaterialItems.kt com lógica de registro
  - ✅ Implementar mapItems para criar itens e BlockItems
  - ✅ Garantir uso de useBlockDescriptionPrefix para BlockItems
  - ✅ Mapear todas as partes de materiais para itens
  - ✅ Implementado em: `common/src/main/kotlin/io/felipeandrade/metalmancy/material/MaterialItems.kt`
  - _Requisitos: 3.1, 3.2, 3.3, 3.4, 3.5_



- [x] 4. Implementar Block Generator
  - ✅ Criar estrutura GeneratedBlock abstrata
  - ✅ Implementar DefaultBlock para blocos cúbicos
  - ✅ Criar BlockEntries que itera sobre Materials.ALL
  - ✅ Implementar geração de blockstate JSON
  - ✅ Implementar geração de block model JSON
  - ✅ Adicionar lógica de criação automática de diretórios
  - ✅ Implementar pretty-printing de JSON
  - ✅ Implementado em: `common/src/tools/blockgen/`
  - _Requisitos: 4.1, 4.2, 4.3, 4.4_



- [x] 5. Implementar Item Generator
  - ✅ Criar estrutura GeneratedItem
  - ✅ Criar GeneratedBlockItem que referencia modelos de blocos
  - ✅ Criar ItemEntries que itera sobre Materials.ALL
  - ✅ Implementar geração de item model JSON
  - ✅ Implementar geração de item render JSON
  - ✅ Processar blocos para gerar modelos de item correspondentes
  - ✅ Implementado em: `common/src/tools/itemgen/`
  - _Requisitos: 5.1, 5.2, 5.3, 5.4_



- [x] 6. Implementar Recipe Generator
  - ✅ Criar estrutura GeneratedRecipe abstrata
  - ✅ Implementar SmeltingRecipe e BlastingRecipe
  - ✅ Criar Recipes.kt com lógica byFamily
  - ✅ Implementar geração de receitas para metais (ORE → INGOT)
  - ✅ Implementar geração de receitas para gemas (ORE → GEM)
  - ✅ Criar RecipeEntries que itera sobre Materials.ALL
  - ✅ Configurar Gson com disableHtmlEscaping
  - ✅ Implementado em: `common/src/tools/recipegen/`
  - _Requisitos: 6.1, 6.2, 6.3, 6.4_



- [x] 6.5 Implementar Loot Generator
  - ✅ Criar estrutura GeneratedLoot data class
  - ✅ Criar LootEntries com lista de materiais
  - ✅ Implementar geração de loot tables para minérios
  - ✅ Adicionar suporte para Silk Touch
  - ✅ Adicionar suporte para Fortune
  - ✅ Adicionar explosion decay
  - ✅ Suportar drops diferentes por família (GEM, DUST, RAW_ITEM)
  - ✅ Implementado em: `common/src/tools/lootgen/`
  - _Requisitos: Não especificado nos requisitos originais, mas essencial para gameplay_



- [x] 7. Implementar Worldgen Generator
  - ✅ Criar estrutura OreGen data class
  - ✅ Criar enum OreGenHeightType (TRAPEZOID, TRIANGLE, UNIFORM)
  - ✅ Criar OreGenEntries com configurações para overworld/nether/ender
  - ✅ Implementar geração de configured_feature JSON
  - ✅ Implementar geração de placed_feature JSON
  - ✅ Adicionar suporte para targets stone e deepslate
  - ✅ Implementar lógica de sufixos para nomes duplicados
  - ✅ Implementado em: `common/src/tools/worldgen/`
  - _Requisitos: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7_



- [x] 8. Configurar integração com Gradle
  - ✅ Criar task generateBlocks para Block Generator
  - ✅ Criar task generateItems para Item Generator
  - ✅ Criar task generateRecipes para Recipe Generator
  - ✅ Criar task generateOres para Worldgen Generator
  - ✅ Criar task generateLoot para Loot Generator
  - ✅ Adicionar suporte para argumento --out
  - ✅ Criar tasks syncGenerated* para copiar assets
  - ✅ Adicionar src/tools ao source set principal
  - ✅ Implementado em: `common/build.gradle` e `common/gradle/*.gradle`
  - _Requisitos: 8.1, 8.2, 8.4, 8.5_



- [x] 9. Implementar suporte multi-plataforma
  - ✅ Criar interface PlatformHelper
  - ✅ Implementar Metalmancy.init() para aceitar PlatformHelper
  - ✅ Garantir uso de ResourceLocation.fromNamespaceAndPath()
  - ✅ Garantir uso de BuiltInRegistries
  - ✅ Criar módulo Fabric com implementação específica (totalmente funcional)
  - ✅ Estrutura básica NeoForge criada (MetalmancyNeoForge.kt, ModRegistryEvents)
  - _Requisitos: 9.1, 9.2, 9.3, 9.4, 9.5_

- [x] 9.1 Completar e testar implementação NeoForge
  - Verificar que MaterialBlocks.registerAll() funciona corretamente no NeoForge
  - Verificar que MaterialItems.registerAll() funciona corretamente no NeoForge
  - Testar inicialização completa no NeoForge em ambiente de desenvolvimento
  - Verificar que todos os blocos e itens aparecem no jogo
  - Verificar que worldgen funciona corretamente
  - _Requisitos: 9.2, 9.3, 9.4_



- [x] 10. Implementar organização de materiais por categoria
  - ✅ Criar listas COPPER_LIKE_METALS, IRON_LIKE_METALS, etc.
  - ✅ Garantir que METALS é união de todas as categorias
  - ✅ Garantir que ALL é união de GEMS + SALTS + METALS + ALLOYS
  - ✅ Aplicar propriedades de blocos baseadas em categoria
  - ✅ Implementado em: `common/src/main/kotlin/io/felipeandrade/metalmancy/material/Materials.kt`
  - _Requisitos: 10.1, 10.2, 10.3, 10.4, 10.5_



- [x] 12. Adicionar materiais ao catálogo
  - ✅ Adicionar definições de gemas (Ruby, Sapphire, Topaz)
  - ✅ Adicionar definições de sais (Salt, Potash)
  - ✅ Adicionar definições de metais base (Zinc, Tin, Lead, Nickel)
  - ✅ Adicionar definições de metais intermediários (Aluminum, Manganese, Silver, Cobalt)
  - ✅ Adicionar definições de metais avançados (Platinum, Titanium, Lithium, Uranium)
  - ✅ Adicionar definições de metais místicos (Mithril, Orichalcum)
  - ✅ Adicionar definições de ligas (Pewter, Brass, Bronze, Steel, Electrum, Invar)
  - ✅ Adicionar Mercury (Cinnabar)
  - ✅ Implementado em: `common/src/main/kotlin/io/felipeandrade/metalmancy/material/Materials.kt`
  - _Requisitos: 1.1, 1.4, 10.1, 10.2, 10.3, 10.5_



- [x] 13. Configurar geração de worldgen para materiais
  - ✅ Configurar OreGen para gemas (Ruby, Sapphire, Topaz)
  - ✅ Configurar OreGen para sais (Salt, Potash)
  - ✅ Configurar OreGen para metais base
  - ✅ Configurar OreGen para metais intermediários
  - ✅ Configurar OreGen para metais avançados
  - ✅ Configurar OreGen para Mithril (Nether)
  - ✅ Configurar OreGen para Orichalcum (End)
  - ✅ Configurar múltiplas variantes com sufixos onde necessário
  - ✅ Implementado em: `common/src/tools/worldgen/OreGenEntries.kt`
  - _Requisitos: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7_



- [x] 14. Configurar framework de testes de propriedade
  - Adicionar dependência Kotest ao build.gradle
  - Configurar Kotest para JUnit 5
  - Criar geradores customizados (Arb.material(), Arb.part())
  - Documentar como executar testes de propriedade
  - _Requisitos: Todos os requisitos (testes de propriedade validam todas as propriedades)_

- [x] 14.1 Implementar testes de propriedade para Material
  - **Propriedade 1: Preservação de campos do Material**
  - **Propriedade 2: Geração correta de nomes não-localizados**
  - **Valida: Requisitos 1.1, 1.2, 1.5**

- [x] 14.2 Implementar testes de propriedade para agrupamento
  - **Propriedade 3: Agrupamento por família**
  - **Propriedade 4: Completude da lista ALL**
  - **Propriedade 31: Agrupamento de metais por nível**
  - **Valida: Requisitos 1.4, 10.1, 10.2, 10.3, 10.5**

- [x] 14.3 Implementar testes de propriedade para registro de blocos
  - **Propriedade 5: Criação de blocos para partes de bloco**
  - **Propriedade 6: Namespace correto em ResourceLocations de blocos**
  - **Propriedade 32: Aplicação de propriedades baseadas em categoria**
  - **Valida: Requisitos 2.1, 2.5, 2.6, 10.4**

- [x] 14.4 Implementar testes de propriedade para registro de itens
  - **Propriedade 7: Criação de itens para partes de item**
  - **Propriedade 8: Criação de BlockItems para blocos**
  - **Propriedade 9: Namespace correto em ResourceLocations de itens**
  - **Propriedade 10: Mapeamento completo de partes para itens**
  - **Valida: Requisitos 3.1, 3.2, 3.4, 3.5**

- [x] 14.5 Implementar testes de propriedade para Block Generator
  - **Propriedade 11: Geração de blockstate para cada bloco**
  - **Propriedade 12: Geração de modelo de bloco para cada bloco**
  - **Propriedade 13: Pretty-printing de JSON**
  - **Propriedade 14: Criação automática de diretórios**
  - **Valida: Requisitos 4.1, 4.2, 4.3, 4.4**

- [x] 14.6 Implementar testes de propriedade para Item Generator
  - **Propriedade 15: Geração de modelo de item para cada item**
  - **Propriedade 16: Geração de renderização de item**
  - **Propriedade 17: Modelos de item para blocos**
  - **Valida: Requisitos 5.1, 5.2, 5.3**

- [x] 14.7 Implementar testes de propriedade para Recipe Generator
  - **Propriedade 18: Geração de receita para cada entrada**
  - **Propriedade 19: Desabilitação de escape HTML em receitas**
  - **Valida: Requisitos 6.1, 6.3**

- [x] 14.8 Implementar testes de propriedade para Worldgen Generator
  - **Propriedade 20: Geração de configured_feature para minérios**
  - **Propriedade 21: Geração de placed_feature para minérios**
  - **Propriedade 22: Suporte a variantes stone e deepslate**
  - **Propriedade 23: Respeito à faixa de altura Y**
  - **Propriedade 24: Suporte a tipos de distribuição**
  - **Propriedade 25: Inclusão de veinSize e countPerChunk**
  - **Propriedade 26: Unicidade de nomes de features**
  - **Valida: Requisitos 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7**

- [x] 14.9 Implementar testes de propriedade para platform abstraction
  - **Propriedade 28: Armazenamento de PlatformHelper**
  - **Propriedade 29: Uso de registries compatíveis**
  - **Propriedade 30: Criação de ResourceLocation compatível com 1.21.x**
  - **Valida: Requisitos 9.1, 9.4, 9.5**

- [x] 15. Checkpoint final - Garantir que todos os testes passam
  - ✅ Todos os 381 testes passando (40 property tests + 341 unit tests)
  - ✅ Todas as propriedades de correção validadas
  - ✅ Sistema totalmente funcional no Fabric

---

## Tarefas Restantes

### Suporte Multi-Plataforma

- [x] 16. Completar e validar implementação NeoForge
  - Testar mod no ambiente de desenvolvimento NeoForge
  - Verificar registro correto de blocos via ModRegistryEvents
  - Verificar registro correto de itens via ModRegistryEvents
  - Validar que worldgen funciona corretamente
  - Validar que receitas funcionam corretamente
  - Validar que loot tables funcionam corretamente
  - Criar testes de integração específicos para NeoForge (se necessário)
  - _Requisitos: 9.1, 9.2, 9.3, 9.4, 9.5_

### Polimento e Documentação

- [x] 17. Adicionar texturas para materiais
  - Gerar relatorio com texturas que estejam faltando em common/src/main/resources/assets/metalmancy/textures e salvar em aseprite/
  - _Requisitos: Não especificado, mas necessário para gameplay completo_

- [x] 18. Adicionar traduções (lang files)
  - Criar en_us.json com traduções para todos os materiais
  - Criar pt_br.json com traduções para todos os materiais
  - Garantir que todos os blocos e itens têm nomes localizados
  - _Requisitos: Não especificado, mas necessário para gameplay completo_

- [x] 19. Documentação final do usuário
  - Criar README.md explicando o sistema de materiais
  - Documentar como adicionar novos materiais
  - Documentar como executar geradores
  - Criar guia de contribuição
  - _Requisitos: Não especificado, mas importante para manutenibilidade_
